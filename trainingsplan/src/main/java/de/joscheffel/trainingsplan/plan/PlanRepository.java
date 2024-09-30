package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.generics.FindByResourcesInIF;
import de.joscheffel.trainingsplan.plan.model.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, String>,
    FindByResourcesInIF<Plan, String> {
//  List<Plan> findAllById(List<String> ids);

  List<Plan> findPlansByIdIn(List<String> ids);
}
