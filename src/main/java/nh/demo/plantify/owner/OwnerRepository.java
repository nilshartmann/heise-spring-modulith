package nh.demo.plantify.owner;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends Repository<Owner, UUID> {

    Optional<Owner> findById(UUID ownerId);

    Owner save(Owner owner);

    List<Owner> findAllByOrderByNameAsc();
}
