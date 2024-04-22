package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.generics.EntityRestController;
import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
public class PlanController extends EntityRestController<PlanRequestDto, PlanResponseDto, String> {

  protected PlanController(
      PlanService planService) {
    super(planService);
  }
}
