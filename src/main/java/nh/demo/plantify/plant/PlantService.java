package nh.demo.plantify.plant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService {

    private static final Logger log = LoggerFactory.getLogger( PlantService.class );

    private final PlantRepository plantRepository;
    private final ApplicationEventPublisher events;

    PlantService(PlantRepository plantRepository, ApplicationEventPublisher events) {
        this.plantRepository = plantRepository;
        this.events = events;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {
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

    public Optional<UUID> findOwnerForPlant(UUID plantId) {
        return plantRepository
            .findById(plantId)
            .map(Plant::getOwnerId)
            ;
    }

}