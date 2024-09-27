package de.joscheffel.trainingsplan.resource_access_control;

import de.joscheffel.trainingsplan.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Resource resource;

  @Enumerated(EnumType.STRING)
  private PermissionType permissionType;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public PermissionType getPermissionType() {
    return permissionType;
  }

  public void setPermissionType(PermissionType permissionType) {
    this.permissionType = permissionType;
  }
}
