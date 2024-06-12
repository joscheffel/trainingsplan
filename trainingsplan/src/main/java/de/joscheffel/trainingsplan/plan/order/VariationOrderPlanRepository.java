package de.joscheffel.trainingsplan.plan.order;

import de.joscheffel.trainingsplan.plan.order.model.SubPlanOrder;
import de.joscheffel.trainingsplan.plan.order.model.VariationOrderPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationOrderPlanRepository extends JpaRepository<VariationOrderPlan, String> {
  List<VariationOrderPlan> findVariationOrderPlansByIdIn(List<String> ids);
}
