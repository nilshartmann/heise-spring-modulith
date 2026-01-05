package nh.demo.plantify.care.suggestion;

public record RecurringCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    int intervalDays
) implements CareTaskSuggestion {
}