package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.exercises.variations.VariationRepository;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.plan.mapper.PlanMapper;
import de.joscheffel.trainingsplan.plan.order.SubPlanOrderRepository;
import de.joscheffel.trainingsplan.plan.order.VariationOrderPlanRepository;
import de.joscheffel.trainingsplan.plan.order.dto.SubPlanOrderDto;
import de.joscheffel.trainingsplan.plan.order.dto.VariationOrderPlanDto;
import de.joscheffel.trainingsplan.plan.order.mapper.SubPlanOrderMapper;
import de.joscheffel.trainingsplan.plan.order.mapper.VariationOrderPlanMapper;
import de.joscheffel.trainingsplan.plan.order.model.SubPlanOrder;
import de.joscheffel.trainingsplan.plan.order.model.VariationOrderPlan;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PlanServiceImpl implements PlanService {

  private static final String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private static final String ERROR_COULDNT_FIND_REFERENCED_SUBPLANS = "Couldn't find the referenced subplans";
  private static final String ERROR_COULDNT_FIND_REFERENCED_VARIATIONS = "Couldn't find the referenced variations";
  private final PlanMapper planMapper;
  private final SubPlanOrderMapper subPlanOrderMapper;
  private final VariationOrderPlanMapper variationOrderPlanMapper;
  private final PlanRepository planRepository;
  private final SubPlanOrderRepository subPlanOrderRepository;
  private final VariationOrderPlanRepository variationOrderPlanRepository;
  private final VariationRepository variationRepository;

  public PlanServiceImpl(PlanMapper planMapper, SubPlanOrderMapper subPlanOrderMapper,
      VariationOrderPlanMapper variationOrderPlanMapper, PlanRepository planRepository,
      SubPlanOrderRepository subPlanOrderRepository,
      VariationOrderPlanRepository variationOrderPlanRepository,
      VariationRepository variationRepository) {
    this.planMapper = planMapper;
    this.subPlanOrderMapper = subPlanOrderMapper;
    this.variationOrderPlanMapper = variationOrderPlanMapper;
    this.planRepository = planRepository;
    this.subPlanOrderRepository = subPlanOrderRepository;
    this.variationOrderPlanRepository = variationOrderPlanRepository;
    this.variationRepository = variationRepository;
  }

  @Override
  public Response<PlanResponseDto> store(PlanRequestDto planRequestDto) {
    var subPlanOrdersResponse = retrieveSubPlanOrders(planRequestDto.subPlanOrders());
    if (subPlanOrdersResponse.isError()) {
      return Response.error(subPlanOrdersResponse.errorMessage());
    }

    var variationOrderPlansResponse = retrieveVariationOrders(
        planRequestDto.variationOrderPlans());
    if (variationOrderPlansResponse.isError()) {
      return Response.error(variationOrderPlansResponse.errorMessage());
    }

    var plan = planMapper.mapPlanRequestDtoToPlan(planRequestDto);
    plan.setSubPlanOrders(subPlanOrdersResponse.entity());
    plan.setVariationOrderPlans(variationOrderPlansResponse.entity());
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
        var subPlanOrdersResponse = retrieveSubPlanOrders(planRequestDto.subPlanOrders());
        if (subPlanOrdersResponse.isError()) {
          return Response.error(subPlanOrdersResponse.errorMessage());
        }

        var variationOrderPlansResponse = retrieveVariationOrders(
            planRequestDto.variationOrderPlans());
        if (variationOrderPlansResponse.isError()) {
          return Response.error(variationOrderPlansResponse.errorMessage());
        }

        var plan = planMapper.mapPlanRequestDtoToPlan(planRequestDto);
        plan.setSubPlanOrders(subPlanOrdersResponse.entity());
        plan.setVariationOrderPlans(variationOrderPlansResponse.entity());
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

  private Response<List<SubPlanOrder>> retrieveSubPlanOrders(
      List<SubPlanOrderDto> subPlanOrderDtos) {
    var subPlansSubmittedWithId = subPlanOrderDtos.stream().filter(
        subPlanOrderDto -> Objects.nonNull(subPlanOrderDto.id()) && StringUtils.hasText(
            subPlanOrderDto.id())).toList();
    var subPlansWithoutId = subPlanOrderDtos.stream().filter(
        subPlanOrderDto -> !(Objects.nonNull(subPlanOrderDto.id()) && StringUtils.hasText(
            subPlanOrderDto.id()))).toList();

    var existingSubPlanOrders = subPlanOrderRepository.findSubPlanOrdersByIdIn(
        subPlansSubmittedWithId.stream().map(SubPlanOrderDto::id).toList());

    var subPlanOrders = new ArrayList<SubPlanOrder>();
    subPlanOrders.addAll(existingSubPlanOrders.stream().map(subPlanOrder -> {
      var tmpSubPlanSubmittedWithId = subPlansSubmittedWithId.stream()
          .filter(subPlanOrderDto -> subPlanOrderDto.id() == subPlanOrder.getId()).findFirst();
      if(tmpSubPlanSubmittedWithId.isPresent()) {
        subPlanOrder.setPlanOrder(tmpSubPlanSubmittedWithId.get().planOrder());
        return subPlanOrder;
      }
      return null;
    }).toList());

    for (SubPlanOrderDto subPlanOrderDto : subPlansWithoutId) {
      var referencedPlan = planRepository.findById(subPlanOrderDto.planId());
      if (referencedPlan.isEmpty()) {
        return Response.error(ERROR_COULDNT_FIND_REFERENCED_SUBPLANS);
      }
      var createdSubPlanOrder = subPlanOrderMapper.mapSubPlanOrderRequestToSubPlanOrder(
          subPlanOrderDto);
      createdSubPlanOrder.setPlan(referencedPlan.get());
      subPlanOrders.add(subPlanOrderRepository.save(createdSubPlanOrder));
    }

    if (subPlanOrders.size() != subPlanOrderDtos.size()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_SUBPLANS);
    }

    return Response.of(subPlanOrders);
  }

  private Response<List<VariationOrderPlan>> retrieveVariationOrders(
      List<VariationOrderPlanDto> variationOrderPlanDtos) {

    var variationOrderPlansSubmittedWithId = variationOrderPlanDtos.stream().filter(
        variationOrderPlanDto -> Objects.nonNull(variationOrderPlanDto.id()) && StringUtils.hasText(
            variationOrderPlanDto.id())).toList();
    var variationOrderPlansWithoutId = variationOrderPlanDtos.stream().filter(
        variationOrderPlanDto -> !(Objects.nonNull(variationOrderPlanDto.id())
            && StringUtils.hasText(variationOrderPlanDto.id()))).toList();

    var existingVariationOrderPlans = variationOrderPlanRepository.findVariationOrderPlansByIdIn(
        variationOrderPlansSubmittedWithId.stream().map(VariationOrderPlanDto::id).toList());

    var variationOrderPlans = new ArrayList<VariationOrderPlan>();
    variationOrderPlans.addAll(existingVariationOrderPlans.stream().map(variationOrderPlan -> {
      var tmpVariationOrderPlanSubmittedWithId = variationOrderPlansSubmittedWithId.stream()
          .filter(variationOrderPlanDto -> Objects.equals(variationOrderPlanDto.id(),
              variationOrderPlan.getId()))
          .findFirst();
      if(tmpVariationOrderPlanSubmittedWithId.isPresent()) {
        variationOrderPlan.setExerciseVariationOrder(
            tmpVariationOrderPlanSubmittedWithId.get().executionOrder()); // Todo: needs to be fixed execution order!
        return variationOrderPlan;
      }
      return null;
    }).toList());

    for (VariationOrderPlanDto variationOrderPlanDto : variationOrderPlansWithoutId) {

      var referencedVariation = variationRepository.findById(variationOrderPlanDto.variationId());
      if (referencedVariation.isEmpty()) {
        return Response.error(ERROR_COULDNT_FIND_REFERENCED_VARIATIONS);
      }
      var createdVariationOrderPlan = variationOrderPlanMapper.mapVariationOrderPlanRequestDtoToVariationOrderPlan(
          variationOrderPlanDto);
      createdVariationOrderPlan.setVariation(referencedVariation.get());
      variationOrderPlans.add(variationOrderPlanRepository.save(createdVariationOrderPlan));
    }

    if (variationOrderPlans.size() != variationOrderPlanDtos.size()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_VARIATIONS);
    }

    return Response.of(variationOrderPlans);
  }
}