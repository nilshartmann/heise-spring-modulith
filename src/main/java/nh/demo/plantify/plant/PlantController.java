package nh.demo.plantify.plant;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/plants")
class PlantController {

    private final PlantService plantService;

    PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    record NewPlantRequest(UUID ownerId, String name, PlantType plantType, String location) {}

    @PostMapping
    Plant addPlant(@RequestBody NewPlantRequest request) {
        return plantService.registerPlant(
            request.ownerId(), 
            request.name(), 
            request.plantType(),
            request.location()
        );
    }
}