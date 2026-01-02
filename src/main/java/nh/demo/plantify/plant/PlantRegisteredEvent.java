package nh.demo.plantify.plant;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public record PlantRegisteredEvent(UUID plantId, UUID ownerId, PlantType plantType, String location)  {
}
