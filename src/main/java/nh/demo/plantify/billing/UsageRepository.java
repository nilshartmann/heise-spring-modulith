package nh.demo.plantify.billing;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

interface UsageRepository extends Repository<UsageRecord, UUID> {

    UsageRecord save(UsageRecord usageRecord);

    @Query("SELECT DISTINCT u.plantId FROM UsageRecord u WHERE u.recordedAt BETWEEN :start AND :end")
    List<UUID> findPlantIdsBetween(Instant start, Instant end);

    List<UsageRecord> findByPlantIdAndRecordedAtBetween(
        UUID plantId, Instant start, Instant end
    );
}