package nh.demo.plantify.owner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
class OwnerController {

    private final OwnerRepository ownerRepository;

    OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping
    List<OwnerDto> getOwners() {
        return ownerRepository.findAllByOrderByNameAsc().stream()
                .map(OwnerDto::fromOwner)
                .toList();
    }
}
