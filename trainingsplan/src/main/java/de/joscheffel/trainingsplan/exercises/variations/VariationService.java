package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface VariationService extends
    EntityService<VariationRequestDto, VariationResponseDto, String> {

  Response<List<VariationResponseDto>> showAllForExerciseId(String id);
}
