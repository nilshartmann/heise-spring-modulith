package nh.demo.plantify.storage;

import nh.demo.plantify.care.CareTaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/plant-storages")
class PlantStorageController {

    private final PlantStorageService plantStorageService;

    PlantStorageController(PlantStorageService plantStorageService) {
        this.plantStorageService = plantStorageService;
    }

    @PostMapping("/{id}/complete")
    ResponseEntity<PlantStorageDto> completeStorage(@PathVariable UUID id) {

        var storage = plantStorageService.complete(id);

        return ResponseEntity.ok(storage);
    }
}
