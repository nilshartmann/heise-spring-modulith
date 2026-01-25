package nh.demo.plantify.owner;

import java.util.UUID;

record OwnerDto(UUID id, String name) {

    static OwnerDto fromOwner(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName());
    }
}
