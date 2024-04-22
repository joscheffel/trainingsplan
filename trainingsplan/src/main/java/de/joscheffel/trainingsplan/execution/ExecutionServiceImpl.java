package de.joscheffel.trainingsplan.execution;

import de.joscheffel.trainingsplan.execution.dtos.ExecutionRequestDto;
import de.joscheffel.trainingsplan.execution.dtos.ExecutionResponseDto;
import de.joscheffel.trainingsplan.execution.mapper.ExecutionMapper;
import de.joscheffel.trainingsplan.exercises.variations.VariationRepository;
import de.joscheffel.trainingsplan.plan.PlanRepository;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExecutionServiceImpl implements ExecutionService {

  private static final String ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION = "Couldn't find the referenced exercise variation";
  private static final String ERROR_COULDNT_FIND_REFERENCED_PLAN = "Couldn't find the referenced plan";
  private static final String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private final ExecutionRepository executionRepository;
  private final ExecutionMapper executionMapper;
  private final VariationRepository variationRepository;
  private final PlanRepository planRepository;

  public ExecutionServiceImpl(ExecutionRepository executionRepository,
      ExecutionMapper executionMapper, VariationRepository variationRepository,
      PlanRepository planRepository) {
    this.executionRepository = executionRepository;
    this.executionMapper = executionMapper;
    this.variationRepository = variationRepository;
    this.planRepository = planRepository;
  }

  @Override
  public Response<ExecutionResponseDto> store(ExecutionRequestDto executionRequestDto) {
    var exerciseVariationOptional = variationRepository.findById(executionRequestDto.variationId());
    var planOptional = planRepository.findById(executionRequestDto.planId());

    if (exerciseVariationOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION);
    } else if (planOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_PLAN);
    }

    var execution = executionMapper.mapExecutionRequestDtoToExecution(executionRequestDto);
    execution.setVariation(exerciseVariationOptional.get());
    execution.setPlan(planOptional.get());
    var storedExecution = executionRepository.save(execution);
    if (executionRepository.existsById(storedExecution.getId())) {
      var executionResponseDto = executionMapper.mapExecutionToExecutionResponseDto(
          storedExecution);
      return Response.of(executionResponseDto);
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<ExecutionResponseDto> show(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var executionOptional = executionRepository.findById(id);
      if (executionOptional.isPresent()) {
        var executionResponseDto = executionMapper.mapExecutionToExecutionResponseDto(
            executionOptional.get());
        return Response.of(executionResponseDto);
      }
      return Response.error("Not Found");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<ExecutionResponseDto>> showAll() {
    var executions = executionRepository.findAll();
    var executionResponseDtos = executionMapper.mapExecutionListToExecutionResponseDtoList(
        executions);
    return Response.of(executionResponseDtos);
  }

  @Override
  public Response<ExecutionResponseDto> update(String id, ExecutionRequestDto executionRequestDto) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (executionRepository.existsById(id)) {
        var exerciseVariationOptional = variationRepository.findById(
            executionRequestDto.variationId());
        var planOptional = planRepository.findById(executionRequestDto.planId());
        if (exerciseVariationOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION);
        } else if (planOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_PLAN);
        }

        var execution = executionMapper.mapExecutionRequestDtoToExecution(executionRequestDto);
        execution.setVariation(exerciseVariationOptional.get());
        execution.setPlan(planOptional.get());
        execution.setId(id);
        var storedExecution = executionRepository.save(execution);
        var executionResponseDto = executionMapper.mapExecutionToExecutionResponseDto(
            storedExecution);
        return Response.of(executionResponseDto);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<ExecutionResponseDto> deleteById(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var executionOptional = executionRepository.findById(id);
      if (executionOptional.isPresent()) {
        executionRepository.deleteById(id);
        if (!executionRepository.existsById(id)) {
          var executionResponseDto = executionMapper.mapExecutionToExecutionResponseDto(
              executionOptional.get());
          return Response.of(executionResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }
}
