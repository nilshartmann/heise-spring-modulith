package nh.demo.plantify.billing.invoice;


import org.springframework.modulith.events.Externalized;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;


record BillingItem(
    String description,
    BigDecimal amount
) {

    static BillingItem of (UsageRecord ur) {
        return new BillingItem(
            ur.getUsageType().toString(),
            BigDecimal.valueOf(ur.getCostCents()).movePointLeft(2)
        );
    }

}

@Externalized(target = "invoices::#{#this.ownerId()}")
public record InvoiceGeneratedEvent(
    LocalDateTime createdAt,
    UUID ownerId,
    String ownerName,
    YearMonth billingPeriod,
    BigDecimal amount,
    List<BillingItem> billingItems

) {
}
