package de.joscheffel.trainingsplan.plan.order;

import de.joscheffel.trainingsplan.plan.order.model.SubPlanOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubPlanOrderRepository extends JpaRepository<SubPlanOrder, String> {
  List<SubPlanOrder> findSubPlanOrdersByIdIn(List<String> ids);
}
