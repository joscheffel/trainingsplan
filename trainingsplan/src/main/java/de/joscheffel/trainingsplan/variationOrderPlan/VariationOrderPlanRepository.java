package de.joscheffel.trainingsplan.variationOrderPlan;

import de.joscheffel.trainingsplan.variationOrderPlan.model.VariationOrderPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationOrderPlanRepository extends JpaRepository<VariationOrderPlan, String> {

}
