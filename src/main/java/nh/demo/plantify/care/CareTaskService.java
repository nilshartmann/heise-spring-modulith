package nh.demo.plantify.care;

import nh.demo.plantify.care.internal.*;
import nh.demo.plantify.care.suggestion.CareTaskSuggestion;
import nh.demo.plantify.care.suggestion.CareTaskSuggestionService;
import nh.demo.plantify.care.suggestion.OneTimeCareTaskSuggestion;
import nh.demo.plantify.care.suggestion.RecurringCareTaskSuggestion;
import nh.demo.plantify.plant.PlantRegisteredEvent;
import nh.demo.plantify.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CareTaskService {

    private static final Logger log = LoggerFactory.getLogger(CareTaskService.class);

    private final CareTaskRepository careTaskRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CareTaskSuggestionService careTaskSuggestionService;

    CareTaskService(CareTaskRepository careTaskRepository, ApplicationEventPublisher applicationEventPublisher, CareTaskSuggestionService careTaskSuggestionService) {
        this.careTaskRepository = careTaskRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.careTaskSuggestionService = careTaskSuggestionService;
    }

    @ApplicationModuleListener
    void onPlantRegistered(PlantRegisteredEvent event) {
        log.debug("Received PlantRegisteredEvent {}", event);
        var suggestionsForPlant = careTaskSuggestionService.getBestSuggestionsByPlantType(
            event.plantType(),
            event.location()
        );

        var careTasks = suggestionsForPlant.stream()
            .map(t -> createFromSuggestion(
                event.plantId(),
                t
            ))
            .toList();

        var savedCareTasks = careTaskRepository.saveAll(careTasks);

        log.info("Created {} CareTasks for plant '{}'. Sending XXXX",
            savedCareTasks.size(),
            event.plantId()
        );

        applicationEventPublisher.publishEvent(
            new InitialCareTasksCreatedEvent(event.plantId())
        );

    }

    @Transactional
    public CareTaskDto completeTask(UUID careTaskId) {
        log.info("Completing care task '{}'", careTaskId);

        var careTask = careTaskRepository.findById(careTaskId)
            .orElseThrow(() -> new ResourceNotFoundException(CareTask.class, careTaskId));

        if (!careTask.isActive()) {
            throw new IllegalStateException("Care Task '%s' is not active".formatted(careTaskId));
        }

        careTask.complete();
        careTaskRepository.save(careTask);

        log.info("Sending CareTaskCompletedEvent for care task '{}'", careTaskId);
        applicationEventPublisher.publishEvent(
            new CareTaskCompletedEvent(
                careTask.getId(),
                careTask.getPlantId(),
                careTask.getType()
            )
        );

        if (!careTask.isActive()) {
            log.info("Sending CareTaskDeactivatedEvent for care task '{}'", careTaskId);
            applicationEventPublisher.publishEvent(
                new CareTaskDeactivatedEvent(
                    careTask.getId(),
                    careTask.getPlantId()
                )
            );
        }

        return CareTaskDto.of(careTask);

    }

    @Transactional(readOnly = true)
    List<CareTaskDto> getAllCareTasks() {
        return careTaskRepository
            .findAll().stream()
            .map(CareTaskDto::of)
            .toList();
    }



    private CareTask createFromSuggestion(UUID plantId, CareTaskSuggestion suggestion) {
        return switch (suggestion) {
            case OneTimeCareTaskSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                s.dueDate(),
                null  // kein Interval
            );
            case RecurringCareTaskSuggestion s -> new CareTask(
                plantId,
                s.taskType(),
                CareTaskSource.SYSTEM,
                LocalDate.now().plusDays(s.intervalDays()),
                s.intervalDays()
            );
        };
    }
}
