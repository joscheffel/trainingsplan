package de.joscheffel.trainingsplan.exercises.mapper;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.exercises.models.Exercise;
import de.joscheffel.trainingsplan.generics.EntityAccessMapperIF;
import de.joscheffel.trainingsplan.resource_access_control.AccessControlWrapper;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper extends EntityAccessMapperIF<Exercise, AccessControlWrapper, ExerciseResponseDto> {

  Exercise mapExerciseRequestDtoToExercise(ExerciseRequestDto exerciseRequestDto);

}
