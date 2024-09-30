package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.generics.AdvancedEntityService;
import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.resource_access_control.PermissionType;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface ExerciseService extends
    AdvancedEntityService<ExerciseRequestDto, ExerciseResponseDto, User, String, KeyValuePair<String, String>> {

//  /**
//   * Retrieves all Exercises owned by the provided owner.
//   *
//   * @param owner The owner for which all Exercises should be retrieved.
//   * @return A {@link Response} containing a list of {@link ExerciseResponseDto} if successful,
//   * otherwise an error response.
//   */
//  Response<List<ExerciseResponseDto>> showAllForOwner(String owner);

}
