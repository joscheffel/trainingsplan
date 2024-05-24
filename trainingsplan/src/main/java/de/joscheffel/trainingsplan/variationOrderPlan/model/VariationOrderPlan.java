package de.joscheffel.trainingsplan.variationOrderPlan.model;

import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.plan.model.Plan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class VariationOrderPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  private Variation variation;

  @ManyToOne
  private Plan plan;

  // private Feedback feedback;

  private int exerciseVariationOrder;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Variation getVariation() {
    return variation;
  }

  public void setVariation(Variation variation) {
    this.variation = variation;
  }

  public Plan getPlan() {
    return plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public int getExerciseVariationOrder() {
    return exerciseVariationOrder;
  }

  public void setExerciseVariationOrder(int exerciseVariationOrder) {
    this.exerciseVariationOrder = exerciseVariationOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VariationOrderPlan variationOrderPlan = (VariationOrderPlan) o;
    return exerciseVariationOrder == variationOrderPlan.exerciseVariationOrder && Objects.equals(id, variationOrderPlan.id)
        && Objects.equals(variation.getId(), variationOrderPlan.variation.getId()) && Objects.equals(plan.getId(),
        variationOrderPlan.plan.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, variation, plan, exerciseVariationOrder);
  }
}
