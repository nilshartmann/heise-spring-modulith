package nh.demo.plantify.plant;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "plants", schema = "plant_schema")
public class Plant {

    @Id
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_type", nullable = false)
    private PlantType plantType;

    @Column(name="location", nullable = false)
    private String location;

    protected Plant() {}

    public Plant(UUID ownerId, String name, PlantType plantType, String location) {
        this.id = UUID.randomUUID();

        this.ownerId = ownerId;
        this.name = name;
        this.plantType = plantType;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public String getLocation() {
        return location;
    }
}
