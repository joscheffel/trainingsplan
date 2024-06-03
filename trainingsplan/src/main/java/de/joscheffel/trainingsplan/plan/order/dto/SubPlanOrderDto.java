package de.joscheffel.trainingsplan.plan.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SubPlanOrderDto(String id, @PositiveOrZero int planOrder, @NotBlank @NotNull String planId) {

}
