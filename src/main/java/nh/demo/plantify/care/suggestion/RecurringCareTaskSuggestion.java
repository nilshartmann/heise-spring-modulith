package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.care.CareTaskType;

public record RecurringCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    int intervalDays
) implements CareTaskSuggestion {
}