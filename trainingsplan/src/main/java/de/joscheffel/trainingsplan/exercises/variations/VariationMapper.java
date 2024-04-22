package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.models.Exercise;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariationMapper {

  Variation mapVariationRequestDtoToVariation(VariationRequestDto variationRequestDto);

  @Mapping(target = "exerciseId", expression = "java(variation.getExercise().getId())")
  VariationResponseDto mapVariationToVariationResponseDto(Variation variation);

  List<VariationResponseDto> mapVariationListToVariationResponseDtoList(
      List<Variation> variationList);
}
