package nh.demo.plantify.billing;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    schema = "billing_schema",
    name = "usage_records"
)
class UsageRecord {
    @Id
    private UUID id;
    
    @Column(name = "plant_id", nullable = false)
    private UUID plantId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_type", nullable = false)
    private UsageType usageType;
    
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "cost_cents", nullable = false)
    private long costCents;

    protected UsageRecord() {}

    UsageRecord(UUID plantId, UsageType usageType, Instant recordedAt, long costCents) {
        this.id = UUID.randomUUID();
        this.plantId = Objects.requireNonNull(plantId);
        this.usageType = Objects.requireNonNull(usageType);
        this.recordedAt = Objects.requireNonNull(recordedAt);
        this.costCents = costCents;
    }

    public UUID getId() { return id; }
    public UUID getClientId() { return plantId; }
    public UsageType getUsageType() { return usageType; }
    public Instant getRecordedAt() { return recordedAt; }

    public long getCostCents() {
        return costCents;
    }
}