package de.joscheffel.trainingsplan.plan.dtos;

import de.joscheffel.trainingsplan.plan.order.dto.SubPlanOrderDto;
import de.joscheffel.trainingsplan.plan.order.dto.VariationOrderPlanDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PlanRequestDto(@NotBlank @Size(max = 200) String name,
                             @Size(max = 200) String description,
                             List<SubPlanOrderDto> subPlanOrders,
                             List<VariationOrderPlanDto> variationOrderPlans) {

}
