package de.joscheffel.trainingsplan.plan.mapper;


import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.plan.model.Plan;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanMapper {

  Plan mapPlanRequestDtoToPlan(PlanRequestDto planRequestDto);


  @Mapping(target = "planIds", source = "plan.plans")
  PlanResponseDto mapPlanToPlanResponseDto(Plan plan);


  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<PlanResponseDto> mapPlanListToPlanResponseDtoList(List<Plan> plans);
}
