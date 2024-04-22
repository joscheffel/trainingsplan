package de.joscheffel.trainingsplan.categories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class CategoryTag {

  @Id
  @GeneratedValue
  private String id;

  private String Name;

  private String Description;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryTag that = (CategoryTag) o;
    return Objects.equals(id, that.id) && Objects.equals(Name, that.Name) && Objects.equals(
        Description, that.Description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, Name, Description);
  }
}
