package de.joscheffel.trainingsplan.exercises.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseRequestDto(@NotBlank(message = "Name is mandatory") @Size(max = 400) String name,
                                 @Size(max = 400) String description,
                                 @Size(max = 400) String pictureUrl,
//                                 List<String> devices, List<String> toolkits,
                                 @Size(max = 400) String owner) {

}
