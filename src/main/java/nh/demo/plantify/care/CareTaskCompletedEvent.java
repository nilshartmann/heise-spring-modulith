package nh.demo.plantify.care;

import java.util.UUID;

public record CareTaskCompletedEvent(
    UUID careTaskId,
    UUID plantId,
    CareTaskType careTaskType) {
}
