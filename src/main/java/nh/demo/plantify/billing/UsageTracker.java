package nh.demo.plantify.billing;

import nh.demo.plantify.billing.invoice.UsageRecord;
import nh.demo.plantify.billing.invoice.UsageRepository;
import nh.demo.plantify.billing.invoice.UsageType;
import nh.demo.plantify.care.CareTaskCompletedEvent;
import nh.demo.plantify.care.InitialCareTasksCreatedEvent;
import nh.demo.plantify.care.suggestion.CareTaskType;
import nh.demo.plantify.storage.PlantStorageCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class UsageTracker {

    private static final Logger log = LoggerFactory.getLogger(UsageTracker.class);
    private final UsageRepository usageRepository;

    UsageTracker(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    @ApplicationModuleListener
    void trackInitialCareTasksCreated(InitialCareTasksCreatedEvent event) {
        log.debug("Tracking InitialCareTasksCreatedEvent={}", event);
        UsageRecord usageRecord = new UsageRecord(
            event.plantId(),
            UsageType.SETUP_FEE,
            Instant.now(),
            1000L
        );

        usageRepository.save(usageRecord);

        log.info("Successfully tracked initial care tasks: usageId={}", usageRecord.getId());
    }

    @ApplicationModuleListener
    void trackCareTaskCompleted(CareTaskCompletedEvent event) {
        log.debug("Tracking CareTaskCompletedEvent={}", event.careTaskId());

        var costs = getCareTaskCostCents(event.careTaskType());

        UsageRecord usageRecord = new UsageRecord(
            event.plantId(),
            UsageType.CARE_TASK_COMPLETED,
            Instant.now(),
            costs
        );

        usageRepository.save(usageRecord);
        log.info("Successfully tracked care task: usageId={}, costs={}", usageRecord.getId(), costs);
    }

    @ApplicationModuleListener
    void trackStorageCompleted(PlantStorageCompletedEvent event) {
        log.debug("Tracking PlantStorageCompletedEvent storageId={}", event.storageId());

        var storageTime =  ChronoUnit.DAYS.between(event.startDate(), event.endDate());
        var storageCostCents = storageTime * 200L;

        UsageRecord usageRecord = new UsageRecord(
            event.plantId(),
            UsageType.STORAGE_COMPLETED,
            Instant.now(),
            storageCostCents
        );

        usageRepository.save(usageRecord);
        log.info("Successfully tracked storage completed: usageId={}, storageCostCents={}", usageRecord.getId(), storageCostCents);
    }


    long getCareTaskCostCents(CareTaskType taskType) {
        var result = switch (taskType) {
            case PRUNING -> 400L;
            case WATERING -> 50L;
            case REPOTTING -> 500L;
            case FERTILIZING -> 100L;
            case PEST_CONTROL -> 300;
        };

        return result;
    }

}
