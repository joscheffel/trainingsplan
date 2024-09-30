package de.joscheffel.trainingsplan.exercises.models;

import de.joscheffel.trainingsplan.resource_access_control.Resource;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String description;
  private String pictureUrl;

//  private List<Devices> devices;
//  private List<String> toolkits;

  @OneToOne(cascade = CascadeType.ALL)
  private Resource resource;

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

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
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
