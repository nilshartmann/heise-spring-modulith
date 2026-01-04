package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.care.CareTaskType;

import java.time.LocalDate;

public record OneTimeCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    LocalDate dueDate
) implements CareTaskSuggestion {}