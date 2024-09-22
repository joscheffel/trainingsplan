package de.joscheffel.trainingsplan.user.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank @Size(max = 200) String pseudonym,
                             @NotNull Integer birthYear) {

}
