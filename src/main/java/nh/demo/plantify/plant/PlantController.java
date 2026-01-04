package nh.demo.plantify.plant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/plants")
class PlantController {

    private static final Logger log = LoggerFactory.getLogger(PlantController.class);

    private final PlantService plantService;

    PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    record NewPlantRequest(UUID ownerId, String name, PlantType plantType, String location) {
    }

    @PostMapping
    Plant addPlant(@RequestBody NewPlantRequest request) {
        log.info("Adding Plant from request {}", request);

        return plantService.registerPlant(
            request.ownerId(),
            request.name(),
            request.plantType(),
            request.location()
        );
    }
}