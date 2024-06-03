package de.joscheffel.trainingsplan.plan.model;

import de.joscheffel.trainingsplan.plan.order.model.SubPlanOrder;
import de.joscheffel.trainingsplan.plan.order.model.VariationOrderPlan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Plan {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  private String description;

//  @Column(columnDefinition = "TIMESTAMP")
//  private LocalDateTime startDateTime;
//
//  @Column(columnDefinition = "TIMESTAMP")
//  private LocalDateTime endDateTime;

  // private List<CategoryTag> tags;

  @OneToMany
  private List<SubPlanOrder> subPlanOrders;

  @OneToMany
  private List<VariationOrderPlan> variationOrderPlans;

  public List<VariationOrderPlan> getVariationOrderPlans() {
    return variationOrderPlans;
  }

  public void setVariationOrderPlans(
      List<VariationOrderPlan> variationOrderPlans) {
    this.variationOrderPlans = variationOrderPlans;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<SubPlanOrder> getSubPlanOrders() {
    return subPlanOrders;
  }

  public void setSubPlanOrders(
      List<SubPlanOrder> subPlanOrders) {
    this.subPlanOrders = subPlanOrders;
  }
}
