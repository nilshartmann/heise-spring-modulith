package nh.demo.plantify.storage;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PlantStorageRepository extends Repository<PlantStorage, UUID> {

    Optional<PlantStorage> findById(UUID id);

    PlantStorage save(PlantStorage storage);



}
