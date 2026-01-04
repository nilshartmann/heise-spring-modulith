package nh.demo.plantify.billing.invoice;


import org.springframework.modulith.events.Externalized;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Externalized(target = "invoices::#{#this.ownerId()}")
public record InvoiceGeneratedEvent(
    UUID ownerId,
    YearMonth billingPeriod,
    BigDecimal amount
) {
}
