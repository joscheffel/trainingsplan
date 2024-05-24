package de.joscheffel.trainingsplan.variationOrderPlan;

import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanRequestDto;
import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanResponseDto;
import de.joscheffel.trainingsplan.variationOrderPlan.mapper.VariationOrderPlanMapper;
import de.joscheffel.trainingsplan.exercises.variations.VariationRepository;
import de.joscheffel.trainingsplan.plan.PlanRepository;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VariationOrderPlanServiceImpl implements VariationOrderPlanService {

  private static final String ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION = "Couldn't find the referenced exercise variation";
  private static final String ERROR_COULDNT_FIND_REFERENCED_PLAN = "Couldn't find the referenced plan";
  private static final String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private final VariationOrderPlanRepository variationOrderPlanRepository;
  private final VariationOrderPlanMapper variationOrderPlanMapper;
  private final VariationRepository variationRepository;
  private final PlanRepository planRepository;

  public VariationOrderPlanServiceImpl(VariationOrderPlanRepository variationOrderPlanRepository,
      VariationOrderPlanMapper variationOrderPlanMapper, VariationRepository variationRepository,
      PlanRepository planRepository) {
    this.variationOrderPlanRepository = variationOrderPlanRepository;
    this.variationOrderPlanMapper = variationOrderPlanMapper;
    this.variationRepository = variationRepository;
    this.planRepository = planRepository;
  }

  @Override
  public Response<VariationOrderPlanResponseDto> store(
      VariationOrderPlanRequestDto variationOrderPlanRequestDto) {
    var exerciseVariationOptional = variationRepository.findById(variationOrderPlanRequestDto.variationId());
    var planOptional = planRepository.findById(variationOrderPlanRequestDto.planId());

    if (exerciseVariationOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION);
    } else if (planOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_PLAN);
    }

    var execution = variationOrderPlanMapper.mapVariationOrderPlanRequestDtoToVariationOrderPlan(
        variationOrderPlanRequestDto);
    execution.setVariation(exerciseVariationOptional.get());
    execution.setPlan(planOptional.get());
    var storedExecution = variationOrderPlanRepository.save(execution);
    if (variationOrderPlanRepository.existsById(storedExecution.getId())) {
      var executionResponseDto = variationOrderPlanMapper.mapVariationOrderPlanToVariationOrderPlanResponseDto(
          storedExecution);
      return Response.of(executionResponseDto);
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationOrderPlanResponseDto> show(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var executionOptional = variationOrderPlanRepository.findById(id);
      if (executionOptional.isPresent()) {
        var executionResponseDto = variationOrderPlanMapper.mapVariationOrderPlanToVariationOrderPlanResponseDto(
            executionOptional.get());
        return Response.of(executionResponseDto);
      }
      return Response.error("Not Found");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<VariationOrderPlanResponseDto>> showAll() {
    var executions = variationOrderPlanRepository.findAll();
    var executionResponseDtos = variationOrderPlanMapper.mapVariationOrderPlanListToVariationOrderPlanResponseDtoList(
        executions);
    return Response.of(executionResponseDtos);
  }

  @Override
  public Response<VariationOrderPlanResponseDto> update(String id, VariationOrderPlanRequestDto variationOrderPlanRequestDto) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (variationOrderPlanRepository.existsById(id)) {
        var exerciseVariationOptional = variationRepository.findById(
            variationOrderPlanRequestDto.variationId());
        var planOptional = planRepository.findById(variationOrderPlanRequestDto.planId());
        if (exerciseVariationOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE_VARIATION);
        } else if (planOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_PLAN);
        }

        var execution = variationOrderPlanMapper.mapVariationOrderPlanRequestDtoToVariationOrderPlan(
            variationOrderPlanRequestDto);
        execution.setVariation(exerciseVariationOptional.get());
        execution.setPlan(planOptional.get());
        execution.setId(id);
        var storedExecution = variationOrderPlanRepository.save(execution);
        var executionResponseDto = variationOrderPlanMapper.mapVariationOrderPlanToVariationOrderPlanResponseDto(
            storedExecution);
        return Response.of(executionResponseDto);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationOrderPlanResponseDto> deleteById(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var executionOptional = variationOrderPlanRepository.findById(id);
      if (executionOptional.isPresent()) {
        variationOrderPlanRepository.deleteById(id);
        if (!variationOrderPlanRepository.existsById(id)) {
          var executionResponseDto = variationOrderPlanMapper.mapVariationOrderPlanToVariationOrderPlanResponseDto(
              executionOptional.get());
          return Response.of(executionResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }
}
