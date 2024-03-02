package de.joscheffel.trainingsplan.exercises.mapper;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.exercises.models.Exercise;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

  Exercise mapExerciseRequestDtoToExercise(ExerciseRequestDto exerciseRequestDto);

  List<ExerciseResponseDto> mapExerciseListToExerciseResponseDtoList(List<Exercise> exerciseList);

  ExerciseResponseDto mapExerciseToExerciseResponseDto(Exercise exercise);

}
