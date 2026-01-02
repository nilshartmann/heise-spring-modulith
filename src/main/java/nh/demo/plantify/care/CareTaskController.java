package nh.demo.plantify.care;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/care-tasks")
class CareTaskController {

    private final CareTaskService careTaskService;

    CareTaskController(CareTaskService careTaskService) {
        this.careTaskService = careTaskService;
    }

    @GetMapping
    List<CareTaskDto> getAllCareTasks() {
        return careTaskService.getAllCareTasks();
    }

    @PostMapping("/{id}/complete")
    ResponseEntity<CareTaskDto> completeTask(@PathVariable UUID id) {

        var careTask = careTaskService.completeTask(id);

        return ResponseEntity.ok(careTask);
    }
}