package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.plant.PlantType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CareTaskSuggestionServiceTest {

    @Test
    void returns_suggestions_from_default_factory() {

        CareTaskSuggestionFactory factory = new DefaultCareTaskSuggestionFactory();
        CareTaskSuggestionService service = new CareTaskSuggestionService(List.of(factory));

        var result = service.getBestSuggestionsByPlantType(PlantType.HERBS, "Keller");
        assertThat(result).satisfiesExactlyInAnyOrder(
            s1 -> assertThat(s1).isInstanceOf(RecurringCareTaskSuggestion.class),
            s2 -> assertThat(s2).isInstanceOf(OneTimeCareTaskSuggestion.class)
        );
    }

    @Test
    void returns_best_suggestions_from_factories() {
        CareTaskSuggestionService service = new CareTaskSuggestionService(List.of(
            // DefaultCareTaskSuggestionFactory fügt Watering (alle 5 Tage) mit confidence 1 hinzu
            new DefaultCareTaskSuggestionFactory(),
            (plantType, location) -> List.of(new RecurringCareTaskSuggestion(CareTaskType.WATERING, 2, 4))
        ));

        var result = service.getBestSuggestionsByPlantType(PlantType.HERBS, "Keller");
        assertThat(result).satisfiesExactlyInAnyOrder(
            s1 -> assertThat(s1).isInstanceOfSatisfying(RecurringCareTaskSuggestion.class, suggestion -> {
                assertThat(suggestion.intervalDays()).isEqualTo(4);
                assertThat(suggestion.confidence()).isEqualTo(2);
            }),
            s2 -> assertThat(s2).isInstanceOf(OneTimeCareTaskSuggestion.class)
        );
    }

    @Test
    void returns_best_for_plant_type() {
        CareTaskSuggestionService service = new CareTaskSuggestionService(List.of(
            (plantType, location) ->
                List.of(new RecurringCareTaskSuggestion(CareTaskType.WATERING, plantType == PlantType.HERBS ? 2 : 1, 8)),
            (plantType, location) ->
                List.of(new RecurringCareTaskSuggestion(CareTaskType.WATERING, plantType == PlantType.ROSES ? 2 : 1, 4))

        ));

        assertThat(
            service.getBestSuggestionsByPlantType(PlantType.HERBS, "Zu Hause")
        ).satisfiesExactlyInAnyOrder(
            s1 -> assertThat(s1).isInstanceOfSatisfying(RecurringCareTaskSuggestion.class, suggestion -> {
                // Bei Kräutern ist sich Task 1 sicher
                assertThat(suggestion.intervalDays()).isEqualTo(8);
                assertThat(suggestion.confidence()).isEqualTo(2);
            })
        );

        assertThat(
            service.getBestSuggestionsByPlantType(PlantType.ROSES, "Zu Hause")
        ).satisfiesExactlyInAnyOrder(
            s1 -> assertThat(s1).isInstanceOfSatisfying(RecurringCareTaskSuggestion.class, suggestion -> {
                // Bei Rosen ist sich Task 2 sicher
                assertThat(suggestion.intervalDays()).isEqualTo(4);
                assertThat(suggestion.confidence()).isEqualTo(2);
            })
        );
    }
}