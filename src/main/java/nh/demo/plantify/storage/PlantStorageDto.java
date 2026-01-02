package nh.demo.plantify.storage;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PlantStorageDto(
    @NotNull UUID storageId,
    @NotNull UUID plantId,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    boolean isCompleted

    ) {

    static PlantStorageDto of(PlantStorage storage) {
        return new PlantStorageDto(
            storage.getId(),
            storage.getPlantId(),
            storage.getStartDate(),
            storage.getEndDate(),
            storage.isCompleted()
        );

    }

}
