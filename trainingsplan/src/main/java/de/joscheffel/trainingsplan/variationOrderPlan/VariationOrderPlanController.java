package de.joscheffel.trainingsplan.variationOrderPlan;

import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanRequestDto;
import de.joscheffel.trainingsplan.variationOrderPlan.dtos.VariationOrderPlanResponseDto;
import de.joscheffel.trainingsplan.generics.EntityRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executions")
public class VariationOrderPlanController extends
    EntityRestController<VariationOrderPlanRequestDto, VariationOrderPlanResponseDto, String> {

  protected VariationOrderPlanController(VariationOrderPlanService variationOrderPlanService) {
    super(variationOrderPlanService);
  }
}
