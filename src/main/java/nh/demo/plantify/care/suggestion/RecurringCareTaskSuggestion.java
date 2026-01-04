package nh.demo.plantify.care.suggestion;

import org.springframework.modulith.NamedInterface;

public record RecurringCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    int intervalDays
) implements CareTaskSuggestion {
}