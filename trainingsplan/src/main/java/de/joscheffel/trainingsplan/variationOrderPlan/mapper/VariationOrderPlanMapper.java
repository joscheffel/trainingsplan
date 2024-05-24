package de.joscheffel.trainingsplan.variationOrderPlan.mapper;

import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanRequestDto;
import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanResponseDto;
import de.joscheffel.trainingsplan.variationOrderPlan.model.VariationOrderPlan;
import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.plan.model.Plan;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariationOrderPlanMapper {


  @Mapping(target = "variationId", source = "variationOrderPlan.variation")
  @Mapping(target = "planId", source = "variationOrderPlan.plan")
  VariationOrderPlanResponseDto mapVariationOrderPlanToVariationOrderPlanResponseDto(VariationOrderPlan variationOrderPlan);

  default String mapVariationToVariationIdString(Variation variation) {
    return Objects.nonNull(variation) ? variation.getId() : null;
  }

  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<VariationOrderPlanResponseDto> mapVariationOrderPlanListToVariationOrderPlanResponseDtoList(List<VariationOrderPlan> variationOrderPlans);

  VariationOrderPlan mapVariationOrderPlanRequestDtoToVariationOrderPlan(
      VariationOrderPlanRequestDto variationOrderPlanRequestDto);
}
