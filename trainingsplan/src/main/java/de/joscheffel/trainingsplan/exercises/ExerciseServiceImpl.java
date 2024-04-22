package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.exercises.mapper.ExerciseMapper;
import de.joscheffel.trainingsplan.utils.Response;
import jakarta.transaction.NotSupportedException;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExerciseServiceImpl implements ExerciseService {

  private final ExerciseRepository exerciseRepository;
  private final ExerciseMapper exerciseMapper;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
    this.exerciseRepository = exerciseRepository;
    this.exerciseMapper = exerciseMapper;
  }

  @Override
  public Response<ExerciseResponseDto> show(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var exerciseOptional = exerciseRepository.findById(id);
      if (exerciseOptional.isPresent()) {
        var exerciseResponseDto = exerciseMapper.mapExerciseToExerciseResponseDto(
            exerciseOptional.get());
        return Response.of(exerciseResponseDto);
      }
      return Response.error("Not Found");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<ExerciseResponseDto>> showAll() {
    var exercises = exerciseRepository.findAll();
    var exerciseResponseDtos = exerciseMapper.mapExerciseListToExerciseResponseDtoList(exercises);
    return Response.of(exerciseResponseDtos);
  }

  @Override
  public Response<List<ExerciseResponseDto>> showAllForOwner(String owner) {
    if (Objects.nonNull(owner) && StringUtils.hasText(owner)) {
      var exercises = exerciseRepository.findAllByOwner(owner);
      var exerciseResponseDtos = exerciseMapper.mapExerciseListToExerciseResponseDtoList(exercises);
      return Response.of(exerciseResponseDtos);
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<ExerciseResponseDto> store(ExerciseRequestDto exerciseRequestDto) {
    var exercise = exerciseMapper.mapExerciseRequestDtoToExercise(exerciseRequestDto);
    var storedExercise = exerciseRepository.save(exercise);
    if (exerciseRepository.existsById(storedExercise.getId())) {
      var exerciseResponseDto = exerciseMapper.mapExerciseToExerciseResponseDto(storedExercise);
      return Response.of(exerciseResponseDto);
    }
    return Response.error("Failed to store the exercise");
  }

  @Override
  public Response<ExerciseResponseDto> update(String id, ExerciseRequestDto exerciseRequestDto) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (exerciseRepository.existsById(id)) {
        var exercise = exerciseMapper.mapExerciseRequestDtoToExercise(exerciseRequestDto);
        exercise.setId(id);
        var storedExercise = exerciseRepository.save(exercise);
        var exerciseResponseDto = exerciseMapper.mapExerciseToExerciseResponseDto(storedExercise);
        return Response.of(exerciseResponseDto);
      }
    }
    return Response.error("Failed to update the exercise");
  }

  @Override
  public Response<ExerciseResponseDto> deleteById(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var exerciseOptional = exerciseRepository.findById(id);
      if (exerciseOptional.isPresent()) {
        exerciseRepository.deleteById(id);
        if (!exerciseRepository.existsById(id)) {
          var exerciseResponseDto = exerciseMapper.mapExerciseToExerciseResponseDto(
              exerciseOptional.get());
          return Response.of(exerciseResponseDto);
        }
      }
    }
    return Response.error("Failed to delete the exercise");
  }
}
