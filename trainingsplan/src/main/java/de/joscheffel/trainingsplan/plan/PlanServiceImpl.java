package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.plan.mapper.PlanMapper;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PlanServiceImpl implements PlanService {

  private static final String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private static final String ERROR_COULDNT_FIND_REFERENCED_SUBPLANS = "Couldn't find the referenced subplans";
  private final PlanMapper planMapper;
  private final PlanRepository planRepository;

  public PlanServiceImpl(PlanMapper planMapper, PlanRepository planRepository) {
    this.planMapper = planMapper;
    this.planRepository = planRepository;
  }

  @Override
  public Response<PlanResponseDto> store(PlanRequestDto planRequestDto) {
    var subPlans = planRepository.findPlansByIdIn(planRequestDto.planIds());

    if (Objects.nonNull(planRequestDto.planIds()) && subPlans.size() != planRequestDto.planIds()
        .size()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_SUBPLANS);
    }

    var plan = planMapper.mapPlanRequestDtoToPlan(planRequestDto);
    plan.setPlans(subPlans);
    var storedPlan = planRepository.save(plan);

    if (planRepository.existsById(storedPlan.getId())) {
      var planResponseDto = planMapper.mapPlanToPlanResponseDto(storedPlan);
      return Response.of(planResponseDto);
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<PlanResponseDto> show(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var planOptional = planRepository.findById(id);
      if (planOptional.isPresent()) {
        var planResponseDto = planMapper.mapPlanToPlanResponseDto(planOptional.get());
        return Response.of(planResponseDto);
      }
      return Response.error("Not Found");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<PlanResponseDto>> showAll() {
    var plans = planRepository.findAll();
    var planResponseDtos = planMapper.mapPlanListToPlanResponseDtoList(plans);
    return Response.of(planResponseDtos);
  }

  @Override
  public Response<PlanResponseDto> update(String id, PlanRequestDto planRequestDto) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (planRepository.existsById(id)) {
        var subPlans = planRepository.findPlansByIdIn(planRequestDto.planIds());

        if (subPlans.size() != planRequestDto.planIds().size()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_SUBPLANS);
        }

        var plan = planMapper.mapPlanRequestDtoToPlan(planRequestDto);
        plan.setPlans(subPlans);
        plan.setId(id);
        var storedPlan = planRepository.save(plan);

        if (planRepository.existsById(storedPlan.getId())) {
          var planResponseDto = planMapper.mapPlanToPlanResponseDto(storedPlan);
          return Response.of(planResponseDto);
        }
        return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<PlanResponseDto> deleteById(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var planOptional = planRepository.findById(id);
      if (planOptional.isPresent()) {
        planRepository.deleteById(id);
        if (!planRepository.existsById(id)) {

          var planResponseDto = planMapper.mapPlanToPlanResponseDto(planOptional.get());
          return Response.of(planResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }
}