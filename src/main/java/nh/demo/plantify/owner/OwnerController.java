package nh.demo.plantify.owner;

import nh.demo.plantify.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owners")
class OwnerController {

    private static final Logger log = LoggerFactory.getLogger( OwnerController.class );

    private final OwnerRepository ownerRepository;

    OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping
    List<OwnerDto> getOwners() {
        log.info("Getting Owners...");
        return ownerRepository.findAllByOrderByNameAsc().stream()
                .map(OwnerDto::of)
                .toList();
    }

    @GetMapping("/{ownerId}")
    OwnerDto getOwnerById(@PathVariable UUID ownerId) {
        return ownerRepository
            .findById(ownerId)
            .map(OwnerDto::of)
            .orElseThrow(() -> new ResourceNotFoundException(Owner.class, ownerId));
    }
}
