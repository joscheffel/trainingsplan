package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;

public interface PlanService extends EntityService<PlanRequestDto, PlanResponseDto, String> {

}
