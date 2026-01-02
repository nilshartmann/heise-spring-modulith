package nh.demo.plantify.care;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InitialCareTasksCreatedEvent(@NotNull UUID plantId) {
}
