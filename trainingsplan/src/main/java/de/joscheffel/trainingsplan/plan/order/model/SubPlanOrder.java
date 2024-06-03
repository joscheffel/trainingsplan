package de.joscheffel.trainingsplan.plan.order.model;

import de.joscheffel.trainingsplan.plan.model.Plan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SubPlanOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private int planOrder;

  @ManyToOne
  @JoinColumn(name = "plan_id", nullable = false)
  private Plan plan;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getPlanOrder() {
    return planOrder;
  }

  public void setPlanOrder(int planOrder) {
    this.planOrder = planOrder;
  }

  public Plan getPlan() {
    return plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }
}
