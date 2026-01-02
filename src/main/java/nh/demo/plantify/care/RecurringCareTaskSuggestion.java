package nh.demo.plantify.care;

public record RecurringCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    int intervalDays
) implements CareTaskSuggestion {
}