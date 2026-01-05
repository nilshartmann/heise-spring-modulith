package nh.demo.plantify.billing.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.moments.MonthHasPassed;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Component
class InvoiceGenerator {

    private static final Logger log = LoggerFactory.getLogger(InvoiceGenerator.class);
    private final UsageRepository usageRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    InvoiceGenerator(UsageRepository usageRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.usageRepository = usageRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    @Transactional
    void generateInvoices(MonthHasPassed event) {
        log.info("Month has passed. Generating invoices for: {}", event.getMonth());

        var month = event.getMonth();
        Instant start = month.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = month.atEndOfMonth().atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        List<UUID> ownerIds = usageRepository.findOwnerIdsBetween(
            start, end
        );

        ownerIds.forEach(plantId -> {
                var cost = calculateCostsEuroForOwner(plantId, start, end);
                // for simplicity we only publish the event
                // but does not write the invoice to the database
                var invoiceCreatedEvent = new InvoiceGeneratedEvent(
                    plantId,
                    month,
                    cost
                );
                applicationEventPublisher.publishEvent(invoiceCreatedEvent);
                log.info("Invoice created for client '{}', published event: {}",
                    plantId, invoiceCreatedEvent
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    private BigDecimal calculateCostsEuroForOwner(UUID ownerId, Instant start, Instant end) {
        var costsCents = usageRepository.getTotalCostsForOwnerRecordedBetween(
            ownerId,
            start,
            end
        );

        return BigDecimal.valueOf(costsCents).movePointLeft(2);
    }


}
