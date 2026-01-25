package nh.demo.plantify.owner;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

interface OwnerRepository extends Repository<Owner, UUID> {

    Owner save(Owner owner);

    List<Owner> findAllByOrderByNameAsc();
}
