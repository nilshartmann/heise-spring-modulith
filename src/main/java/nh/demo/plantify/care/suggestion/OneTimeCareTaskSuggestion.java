package nh.demo.plantify.care.suggestion;

import java.time.LocalDate;

public record OneTimeCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    LocalDate dueDate
) implements CareTaskSuggestion {}