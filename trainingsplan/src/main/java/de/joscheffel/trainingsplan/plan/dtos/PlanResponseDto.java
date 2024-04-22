package de.joscheffel.trainingsplan.plan.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record PlanResponseDto(String id, String name, String description, int planOrder,
                              LocalDateTime startDateTime, LocalDateTime endDateTime,
                              List<String> planIds) {

}
