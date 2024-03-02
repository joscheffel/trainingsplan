package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface ExerciseService {

  /**
   * Retrieves Exercise information based on the provided ID.
   *
   * @param id The unique identifier of the Exercise.
   * @return A {@link Response} containing {@link ExerciseResponseDto} if successful, otherwise an
   * error response.
   */
  Response<ExerciseResponseDto> show(String id);

  /**
   * Retrieves all Exercises owned by the provided owner.
   *
   * @param owner The owner for which all Exercises should be retrieved.
   * @return A {@link Response} containing a list of {@link ExerciseResponseDto} if successful,
   * otherwise an error response.
   */
  Response<List<ExerciseResponseDto>> showAllForOwner(String owner);

  /**
   * Stores a new Exercise based on the provided data.
   *
   * @param exerciseRequestDto The {@link ExerciseRequestDto} containing data for the new Exercise.
   * @return A {@link Response} containing the created {@link ExerciseResponseDto} if successful,
   * otherwise an error response.
   */
  Response<ExerciseResponseDto> store(ExerciseRequestDto exerciseRequestDto);

  /**
   * Updates an existing Exercise with the provided ID.
   *
   * @param id                 The unique identifier of the Exercise to be updated.
   * @param exerciseRequestDto The {@link ExerciseRequestDto} containing updated data.
   * @return A {@link Response} containing the updated {@link ExerciseResponseDto} if successful,
   * otherwise an error response.
   */
  Response<ExerciseResponseDto> update(String id, ExerciseRequestDto exerciseRequestDto);

  /**
   * Deletes the Exercise with the provided ID.
   *
   * @param id The unique identifier of the Exercise to be deleted.
   * @return A {@link Response} containing the deleted {@link ExerciseResponseDto} if successful
   * otherwise an error response.
   */
  Response<ExerciseResponseDto> deleteById(String id);

}
