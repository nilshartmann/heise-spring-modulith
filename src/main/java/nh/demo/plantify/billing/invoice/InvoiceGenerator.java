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
import java.util.Map;
import java.util.UUID;

@Component
class InvoiceGenerator {

    private static final Logger log = LoggerFactory.getLogger(InvoiceGenerator.class);
    private final UsageRepository usageRepository;

    private final static Map<UsageType, BigDecimal> prices = Map.of(
        UsageType.CARE_TASK_COMPLETED, new BigDecimal("0.50"),
        UsageType.STORAGE_COMPLETED, new BigDecimal("12.00")
    );
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

        List<UUID> plantIds = usageRepository.findPlantIdsBetween(
            start, end
        );

        plantIds.forEach(plantId -> {
                var cost = calculateAmountForPlant(plantId, start, end);
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
            }
        );
    }

    private BigDecimal calculateAmountForPlant(UUID plantId, Instant start, Instant end) {
        var usages = usageRepository.findByPlantIdAndRecordedAtBetween(
            plantId,
            start,
            end
        );

        BigDecimal amount = usages
            .stream()
            .map(usageRecord -> prices.get(usageRecord.getUsageType()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return amount;
    }


}
