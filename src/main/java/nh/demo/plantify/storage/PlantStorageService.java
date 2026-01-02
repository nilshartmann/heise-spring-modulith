package nh.demo.plantify.storage;

import nh.demo.plantify.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PlantStorageService {
    
    private static final Logger log = LoggerFactory.getLogger( PlantStorageService.class );

    private final PlantStorageRepository plantStorageRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PlantStorageService(PlantStorageRepository plantStorageRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plantStorageRepository = plantStorageRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public PlantStorageDto complete(UUID storageId) {
        log.info("Compleing PlantStorage {}", storageId);

        var storage = plantStorageRepository.findById(storageId)
            .orElseThrow(() -> new ResourceNotFoundException(PlantStorage.class, storageId)
            );

        storage.complete();
        plantStorageRepository.save(storage);
        
        log.debug("Storage completed: {}", storage.getId());

        applicationEventPublisher.publishEvent(
            new PlantStorageCompletedEvent(
                storage.getId(),
                storage.getPlantId(),
                storage.getStartDate(),
                storage.getEndDate()
            )
        );

        return PlantStorageDto.of(storage);
    }

}
