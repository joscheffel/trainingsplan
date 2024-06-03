package de.joscheffel.trainingsplan.plan.order.mapper;

import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.plan.model.Plan;
import de.joscheffel.trainingsplan.plan.order.dto.VariationOrderPlanDto;
import de.joscheffel.trainingsplan.plan.order.model.VariationOrderPlan;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariationOrderPlanMapper {


  @Mapping(target = "variationId", source = "variationOrderPlan.variation")
  @Mapping(target = "executionOrder", source = "variationOrderPlan.exerciseVariationOrder")
  VariationOrderPlanDto mapVariationOrderPlanToVariationOrderPlanResponseDto(
      VariationOrderPlan variationOrderPlan);

  default String mapVariationToVariationIdString(Variation variation) {
    return Objects.nonNull(variation) ? variation.getId() : null;
  }

  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<VariationOrderPlan> mapVariationOrderPlanListToVariationOrderPlanResponseDtoList(
      List<VariationOrderPlan> variationOrderPlans);


  @Mapping(target = "exerciseVariationOrder", source = "variationOrderPlanDto.executionOrder")
  VariationOrderPlan mapVariationOrderPlanRequestDtoToVariationOrderPlan(
      VariationOrderPlanDto variationOrderPlanDto);
}
