package de.joscheffel.trainingsplan.exercises.models;

import de.joscheffel.trainingsplan.devices.dtos.DeviceResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String description;
  private String pictureUrl;

//  private List<DeviceResponseDto> devices;
//  private List<String> toolkits;
  private String owner;

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

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

//  public List<DeviceResponseDto> getDevices() {
//    return devices;
//  }
//
//  public void setDevices(List<DeviceResponseDto> devices) {
//    this.devices = devices;
//  }
//
//  public List<String> getToolkits() {
//    return toolkits;
//  }
//
//  public void setToolkits(List<String> toolkits) {
//    this.toolkits = toolkits;
//  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    Exercise exercise = (Exercise) o;
//    return Objects.equals(id, exercise.id) && Objects.equals(name, exercise.name) && Objects.equals(
//        description, exercise.description) && Objects.equals(pictureUrl, exercise.pictureUrl)
//        && Objects.equals(devices, exercise.devices) && Objects.equals(toolkits, exercise.toolkits)
//        && Objects.equals(owner, exercise.owner);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, name, description, pictureUrl, devices, toolkits, owner);
//  }
}
