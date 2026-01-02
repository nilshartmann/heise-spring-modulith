package nh.demo.plantify.care;

import nh.demo.plantify.care.CareTaskType;
import nh.demo.plantify.care.OneTimeCareTaskSuggestion;
import nh.demo.plantify.care.RecurringCareTaskSuggestion;

public sealed interface CareTaskSuggestion
    permits OneTimeCareTaskSuggestion, RecurringCareTaskSuggestion {

    CareTaskType taskType();
    int confidence();
}