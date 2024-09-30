package de.joscheffel.trainingsplan.exercises.variations.dtos;

import de.joscheffel.trainingsplan.resource_access_control.AccessControlWrapper;
import java.util.List;

public record VariationResponseDto(String id, String exerciseId, String description,
                                   List<AccessControlWrapper> access) {

}
