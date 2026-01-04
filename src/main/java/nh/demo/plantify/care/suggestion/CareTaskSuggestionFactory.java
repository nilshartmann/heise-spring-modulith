package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.plant.PlantType;
import org.springframework.modulith.NamedInterface;

import java.util.List;


public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}

