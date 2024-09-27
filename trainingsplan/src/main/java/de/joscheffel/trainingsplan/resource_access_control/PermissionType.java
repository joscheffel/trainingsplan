package de.joscheffel.trainingsplan.resource_access_control;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PermissionType {
  OWNER("owner"), VIEWER("viewer"), EDITOR("editor");

  private final String value;

  PermissionType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static PermissionType fromValue(String value) {
    for (PermissionType permissionType : PermissionType.values()) {
      if (permissionType.value.equalsIgnoreCase(value)) {
        return permissionType;
      }
    }
    throw new IllegalArgumentException("Unknown permission type: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
