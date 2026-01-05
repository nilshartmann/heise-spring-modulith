package nh.demo.plantify.care;

import nh.demo.plantify.care.suggestion.CareTaskType;

import java.time.LocalDate;
import java.util.UUID;

record CareTaskDto(
    UUID id,
    UUID plantId,
    CareTaskType type,
    CareTaskSource source,
    LocalDate nextDueDate,
    boolean recurring,
    boolean active
) {
    public static CareTaskDto of(CareTask careTask) {
        return new CareTaskDto(
            careTask.getId(),
            careTask.getPlantId(),
            careTask.getType(),
            careTask.getSource(),
            careTask.getNextDueDate(),
            careTask.isRecurring(),
            careTask.isActive()
        );
    }
}