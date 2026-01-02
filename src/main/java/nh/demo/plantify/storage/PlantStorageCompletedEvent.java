package nh.demo.plantify.storage;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PlantStorageCompletedEvent(
    @NotNull UUID storageId,
    @NotNull UUID plantId,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate) {
}
