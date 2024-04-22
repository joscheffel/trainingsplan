package de.joscheffel.trainingsplan.execution.mapper;

import de.joscheffel.trainingsplan.execution.dtos.ExecutionRequestDto;
import de.joscheffel.trainingsplan.execution.dtos.ExecutionResponseDto;
import de.joscheffel.trainingsplan.execution.model.Execution;
import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.plan.model.Plan;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExecutionMapper {


  @Mapping(target = "variationId", source = "execution.variation")
  @Mapping(target = "planId", source = "execution.plan")
  ExecutionResponseDto mapExecutionToExecutionResponseDto(Execution execution);

  default String mapVariationToVariationIdString(Variation variation) {
    return Objects.nonNull(variation) ? variation.getId() : null;
  }

  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<ExecutionResponseDto> mapExecutionListToExecutionResponseDtoList(List<Execution> executions);

  Execution mapExecutionRequestDtoToExecution(ExecutionRequestDto executionRequestDto);
}
