package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
class DefaultCareTaskSuggestionFactory implements CareTaskSuggestionFactory {

    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
        // Nur Defaults: spezialisierte Implementierungen einer CareTaskSuggestionFactory
        // sollten bessere Werte liefern (z.B. anhängig von PlantType und Location)
        return List.of(
            // Jede Pflanze einmal umtopfen
            new OneTimeCareTaskSuggestion(CareTaskType.REPOTTING, 1, LocalDate.now().plusDays(1)),

            // Jede Pflanze alle fünf Tage wässern
            new RecurringCareTaskSuggestion(CareTaskType.WATERING, 1, 5)
        );
    }
}
