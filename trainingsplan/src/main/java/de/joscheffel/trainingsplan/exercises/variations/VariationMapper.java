package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.generics.EntityAccessMapperIF;
import de.joscheffel.trainingsplan.resource_access_control.AccessControlWrapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariationMapper extends
    EntityAccessMapperIF<Variation, AccessControlWrapper, VariationResponseDto> {

  Variation mapVariationRequestDtoToVariation(VariationRequestDto variationRequestDto);

  @Mapping(target = "exerciseId", expression = "java(variation.getExercise().getId())")
  VariationResponseDto mapVariationToVariationResponseDto(Variation variation);

  @Mapping(target = "exerciseId", expression = "java(variation.getExercise().getId())")
  VariationResponseDto mapEntityAndAccessToEntityResponseDto(Variation variation,
      List<AccessControlWrapper> access);

  List<VariationResponseDto> mapVariationListToVariationResponseDtoList(
      List<Variation> variationList);
}
