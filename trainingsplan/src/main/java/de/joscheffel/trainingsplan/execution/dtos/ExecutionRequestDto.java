package de.joscheffel.trainingsplan.execution.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ExecutionRequestDto(@NotBlank @Size(max = 100) String variationId,
                                  @Size(max = 100) String planId,
                                  @PositiveOrZero(message = "execution order has to be greater or equal zero") int executionOrder) {

}
