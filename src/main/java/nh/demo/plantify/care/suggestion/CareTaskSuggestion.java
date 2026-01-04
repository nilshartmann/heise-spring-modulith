package nh.demo.plantify.care.suggestion;

public sealed interface CareTaskSuggestion
    permits OneTimeCareTaskSuggestion, RecurringCareTaskSuggestion {

    CareTaskType taskType();
    int confidence();
}