package nh.demo.plantify.plant;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;


interface PlantRepository extends Repository<Plant, UUID> {

    Plant save(Plant plant);

    Optional<Plant> findById(UUID id);

    boolean existsById(UUID id);

    boolean existsByOwnerIdAndName(UUID ownerId, String name);
}
