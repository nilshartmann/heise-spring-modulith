package nh.demo.plantify.plant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
class PlantService {

    private static final Logger log = LoggerFactory.getLogger( PlantService.class );

    private final PlantRepository plantRepository;
    private final ApplicationEventPublisher events;

    PlantService(PlantRepository plantRepository, ApplicationEventPublisher events) {
        this.plantRepository = plantRepository;
        this.events = events;
    }

    @Transactional
    public Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {
        // Keine Duplikate für denselben Owner
        if (plantRepository.existsByOwnerIdAndName(ownerId, name)) {
            throw new IllegalArgumentException("Plant with name '%s' already exists for this owner".formatted(name));
        }

        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);


        log.debug("Publishing PlantRegisteredEvent");
        events.publishEvent(new PlantRegisteredEvent(
            plant.getId(),
            plant.getOwnerId(),
            plant.getPlantType(),
            plant.getLocation()
        ));

        return plant;
    }

}