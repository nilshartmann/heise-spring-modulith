package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.care.CareTaskType;

public sealed interface CareTaskSuggestion
    permits OneTimeCareTaskSuggestion, RecurringCareTaskSuggestion {

    CareTaskType taskType();
    int confidence();
}