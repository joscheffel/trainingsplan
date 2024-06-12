package de.joscheffel.trainingsplan.plan.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VariationOrderPlanDto(String id, @NotBlank @Size(max = 100) String variationId,
                                    @PositiveOrZero(message = "execution order has to be greater or equal zero") int executionOrder) {

}
