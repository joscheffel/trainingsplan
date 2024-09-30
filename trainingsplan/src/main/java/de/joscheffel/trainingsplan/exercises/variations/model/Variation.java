package de.joscheffel.trainingsplan.exercises.variations.model;

import de.joscheffel.trainingsplan.exercises.models.Exercise;
import de.joscheffel.trainingsplan.resource_access_control.Resource;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Variation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  private Exercise exercise;

  private String description;

  private String owner;

//  private VariationType variationType;
//
//  private List<CategoryTag> categoryTag;

  @OneToOne(cascade = CascadeType.ALL)
  private Resource resource;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Exercise getExercise() {
    return exercise;
  }

  public void setExercise(Exercise exercise) {
    this.exercise = exercise;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variation variation = (Variation) o;
    return Objects.equals(id, variation.id) && Objects.equals(exercise.getId(),
        variation.exercise.getId()) && Objects.equals(description, variation.description)
        && Objects.equals(owner, variation.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, exercise, description, owner);
  }
}
