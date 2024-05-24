package de.joscheffel.trainingsplan.variationOrderPlan;

import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanRequestDto;
import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanResponseDto;
import de.joscheffel.trainingsplan.generics.EntityService;

public interface VariationOrderPlanService extends
    EntityService<VariationOrderPlanRequestDto, VariationOrderPlanResponseDto, String> {

}
