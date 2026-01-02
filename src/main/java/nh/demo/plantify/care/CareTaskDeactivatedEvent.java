package nh.demo.plantify.care;

import java.util.UUID;

public record CareTaskDeactivatedEvent(UUID careTaskId,
                                       UUID plantId) {
}
