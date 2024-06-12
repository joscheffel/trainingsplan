package de.joscheffel.trainingsplan.plan.dtos;

import de.joscheffel.trainingsplan.plan.order.dto.SubPlanOrderDto;
import de.joscheffel.trainingsplan.plan.order.dto.VariationOrderPlanDto;
import java.util.List;

public record PlanResponseDto(String id, String name, String description,
                              List<SubPlanOrderDto> subPlanOrders,
                              List<VariationOrderPlanDto> variationOrderPlans) {

}
