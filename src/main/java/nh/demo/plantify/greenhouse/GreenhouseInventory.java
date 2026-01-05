package nh.demo.plantify.greenhouse;

import nh.demo.plantify.plant.PlantRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class GreenhouseInventory {

    private static final Logger log = LoggerFactory.getLogger( GreenhouseInventory.class );

    @ApplicationModuleListener
    void handlePlantRegistered(PlantRegisteredEvent event) {
        log.info("Received PlantRegisteredEvent - Reserving greenhouse storage {}", event);
    }

}
