package de.joscheffel.trainingsplan.plan.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record PlanRequestDto(@NotBlank @Size(max = 200) String name,
                             @Size(max = 200) String description,
                             @PositiveOrZero int planOrder,
                             LocalDateTime startDateTime,
                             LocalDateTime endDateTime,
                             List<String> planIds) {

}
