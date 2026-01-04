package nh.demo.plantify.storage;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "plant_storages", schema = "storage_schema")
public class PlantStorage {

    @Id
    private UUID id;

    @Column(name = "plant_id", nullable = false)
    private UUID plantId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    protected PlantStorage() {}

    public PlantStorage(UUID plantId, LocalDate startDate) {
        this.id = UUID.randomUUID();
        this.plantId = Objects.requireNonNull(plantId);
        this.startDate = Objects.requireNonNull(startDate);
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlantId() {
        return plantId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void complete() {
        this.endDate = LocalDate.now();
    }

    public boolean isCompleted() {
        return this.endDate != null;
    }
}
