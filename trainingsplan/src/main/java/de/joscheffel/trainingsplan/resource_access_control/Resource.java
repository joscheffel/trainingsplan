package de.joscheffel.trainingsplan.resource_access_control;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String entityId;

  private String entityType;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }
}
