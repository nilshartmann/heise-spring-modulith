package nh.demo.plantify.billing.invoice;

import nh.demo.plantify.TestcontainersConfiguration;
import nh.demo.plantify.plant.PlantService;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.modulith.moments.support.TimeMachine;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.kafka.KafkaContainer;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
    "spring.modulith.moments.enable-time-machine=true",
})
@Sql("/test-usage-data.sql")
class InvoiceGeneratorTest {

    private static final Logger log = LoggerFactory.getLogger( InvoiceGeneratorTest.class );

    @Autowired
    TimeMachine timeMachine;

    @MockitoBean
    PlantService plantService;

    @Autowired
    private KafkaContainer kafkaContainer;

    @TestConfiguration
    static class Config {
        @Bean
        Clock clock() {
            Clock clock = Clock.fixed(
                Instant.parse("2025-11-29T08:15:00.00Z"),
                ZoneId.of("Europe/Berlin")
            );
            return clock;
        }

        @Bean
        NewTopic invoicesTopic() {
            return new NewTopic("invoices", 1, (short) 1);
        }
    }

    private final List<InvoiceGeneratedEvent> recordedKafkaMessages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "invoices", groupId = "test-1")
    public void handle(@Payload InvoiceGeneratedEvent e,
                       @Header(KafkaHeaders.RECEIVED_KEY) String clientId) {
        assertThat(e.ownerId().toString()).isEqualTo(clientId);
        this.recordedKafkaMessages.add(e);
    }

    @Test
    void invoice_is_created_every_month_and_send_to_kafka(Scenario scenario) {
        scenario
            .stimulate(() -> {
                timeMachine.shiftBy(Duration.ofDays(2));
            })
            .andWaitForStateChange(() -> {
                if (recordedKafkaMessages.isEmpty()) {
                    return null;
                }
                return recordedKafkaMessages;
            })
            .andVerify(messages -> {
                assertThat(messages)
                    .extracting(InvoiceGeneratedEvent::ownerId)
                    .containsExactlyInAnyOrder(
                        UUID.fromString("5ec3f30d-4df6-4080-a5dc-560e8db94259"),
                        UUID.fromString("1c7132f5-0e91-420c-9987-7f6ac7911d3c")
                    );
            });
    }

}
