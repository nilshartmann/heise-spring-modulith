package nh.demo.plantify.roses;

import nh.demo.plantify.care.suggestion.CareTaskSuggestion;
import nh.demo.plantify.care.suggestion.CareTaskSuggestionFactory;
import nh.demo.plantify.care.suggestion.CareTaskType;
import nh.demo.plantify.care.suggestion.OneTimeCareTaskSuggestion;
import nh.demo.plantify.plant.PlantType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
class RoseCareTaskSuggestionFactory implements CareTaskSuggestionFactory {
    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
        if (plantType == PlantType.ROSES) {
            return List.of(
                new OneTimeCareTaskSuggestion(CareTaskType.WATERING, 10, LocalDate.now().plusDays(10))
            );
        }

        return List.of();
    }
}
