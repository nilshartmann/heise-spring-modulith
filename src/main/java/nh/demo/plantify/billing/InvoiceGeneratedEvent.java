package nh.demo.plantify.billing;


import org.springframework.modulith.events.Externalized;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Externalized("invoices::#{#this.clientId()}")
public record InvoiceGeneratedEvent(
    UUID ownerId,
    YearMonth billingPeriod,
    BigDecimal amount
) {
}
