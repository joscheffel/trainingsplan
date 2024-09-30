package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.generics.AdvancedEntityService;
import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.user.model.User;

public interface PlanService extends
    AdvancedEntityService<PlanRequestDto, PlanResponseDto, User, String, KeyValuePair<String, String>> {

}
