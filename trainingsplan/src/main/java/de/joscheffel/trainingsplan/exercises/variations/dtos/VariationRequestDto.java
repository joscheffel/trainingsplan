package de.joscheffel.trainingsplan.exercises.variations.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VariationRequestDto(@NotBlank @Size(max = 100) String exerciseId,
                                  @Size(max = 400) String description,
                                  @Size(max = 400) String owner) {

}
