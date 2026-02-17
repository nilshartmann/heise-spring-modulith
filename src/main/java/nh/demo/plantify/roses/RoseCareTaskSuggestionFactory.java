package nh.demo.plantify.roses;

import nh.demo.plantify.care.suggestion.*;
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
                // Rosen müssten TÄGLICH gegossen werden!
                new RecurringCareTaskSuggestion(CareTaskType.WATERING, 10, 1)
            );
        }

        return List.of();
    }
}
