package de.joscheffel.trainingsplan.plan.order.model;

import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class VariationOrderPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "variation_id", nullable = false)
  private Variation variation;

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
    return exerciseVariationOrder == variationOrderPlan.exerciseVariationOrder && Objects.equals(id,
        variationOrderPlan.id) && Objects.equals(variation.getId(),
        variationOrderPlan.variation.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, variation, exerciseVariationOrder);
  }
}
