package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.generics.AdvancedEntityService;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface VariationService extends
    AdvancedEntityService<VariationRequestDto, VariationResponseDto, User, String, KeyValuePair<String, String>> {

  Response<List<VariationResponseDto>> showAllForExerciseId(String id, User requestingUser);
}
