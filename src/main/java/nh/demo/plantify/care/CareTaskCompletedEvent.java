package nh.demo.plantify.care;

import nh.demo.plantify.care.suggestion.CareTaskType;

import java.util.UUID;

public record CareTaskCompletedEvent(
    UUID careTaskId,
    UUID plantId,
    CareTaskType careTaskType) {
}
