package nh.demo.plantify.billing.invoice;

import nh.demo.plantify.owner.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.moments.MonthHasPassed;
import org.springframework.modulith.moments.support.TimeMachine;
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
    private final TimeMachine timeMachine;
    private final OwnerRepository ownerRepository;

    InvoiceGenerator(UsageRepository usageRepository, ApplicationEventPublisher applicationEventPublisher, TimeMachine timeMachine, OwnerRepository ownerRepository) {
        this.usageRepository = usageRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.timeMachine = timeMachine;
        this.ownerRepository = ownerRepository;
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

        var invoiceCreatedAt = timeMachine.now();

        ownerIds.forEach(ownerId -> {
                var usageRecords = usageRepository.getUsagesForOwnerRecordedBetween(
                    ownerId, start, end
                );


                var owner = ownerRepository.findById(ownerId).orElseThrow();
                // for simplicity we only publish the event
                // but does not write the invoice to the database

                var amount = BigDecimal.valueOf(
                        usageRecords
                            .stream()
                            .mapToLong(UsageRecord::getCostCents)
                            .sum()
                    )
                    .movePointLeft(2);

                var invoiceCreatedEvent = new InvoiceGeneratedEvent(
                    invoiceCreatedAt,
                    ownerId,
                    owner.getName(),
                    month,
                    amount,
                    usageRecords.stream().map(BillingItem::of).toList()
                );

                applicationEventPublisher.publishEvent(invoiceCreatedEvent);

                log.info("Invoice created for client '{}', published event: {}",
                    ownerId, invoiceCreatedEvent
                );
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
