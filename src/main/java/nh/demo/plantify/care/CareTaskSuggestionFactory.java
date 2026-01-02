package nh.demo.plantify.care;

import nh.demo.plantify.plant.PlantType;

import java.util.List;
import java.util.Optional;

public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}

