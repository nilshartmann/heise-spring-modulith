package nh.demo.plantify.care.suggestion;

import org.springframework.modulith.NamedInterface;

import java.time.LocalDate;

public record OneTimeCareTaskSuggestion(
    CareTaskType taskType,
    int confidence,
    LocalDate dueDate
) implements CareTaskSuggestion {}