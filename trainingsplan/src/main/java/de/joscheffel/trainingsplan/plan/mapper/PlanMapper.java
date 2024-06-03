package de.joscheffel.trainingsplan.plan.mapper;


import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.plan.model.Plan;
import de.joscheffel.trainingsplan.plan.order.mapper.SubPlanOrderMapper;
import de.joscheffel.trainingsplan.plan.order.mapper.VariationOrderPlanMapper;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubPlanOrderMapper.class, VariationOrderPlanMapper.class})
public interface PlanMapper {
  Plan mapPlanRequestDtoToPlan(PlanRequestDto planRequestDto);


  @Mapping(target = "subPlanOrders", source = "plan.subPlanOrders")
  @Mapping(target = "variationOrderPlans", source = "plan.variationOrderPlans")
  PlanResponseDto mapPlanToPlanResponseDto(Plan plan);


  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<PlanResponseDto> mapPlanListToPlanResponseDtoList(List<Plan> plans);
}
