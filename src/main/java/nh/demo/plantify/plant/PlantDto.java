package nh.demo.plantify.plant;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Mehr oder minder identisch mit Plant, Sinn eines eigenen DTOs hier zweifelhaft, aber für Demo benötigt
 */
record PlantDto(@NotNull UUID id, @NotNull UUID ownerId, @NotNull String name, @NotNull String location) {

    static PlantDto of(Plant plant) {
        return new PlantDto(plant.getId(), plant.getOwnerId(), plant.getName(), plant.getLocation()
        );
    }


}
