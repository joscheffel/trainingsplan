package de.joscheffel.trainingsplan.exercises.models;

import de.joscheffel.trainingsplan.devices.dtos.DeviceResponseDto;
import java.util.List;

public final class ExerciseBuilder {

  private String id;
  private String name;
  private String description;
  private String pictureUrl;
  private List<DeviceResponseDto> devices;
  private List<String> toolkits;
  private String owner;

  private ExerciseBuilder() {
  }

  public static ExerciseBuilder anExercise() {
    return new ExerciseBuilder();
  }

  public ExerciseBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public ExerciseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ExerciseBuilder withPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }

  public ExerciseBuilder withDevices(List<DeviceResponseDto> devices) {
    this.devices = devices;
    return this;
  }

  public ExerciseBuilder withToolkits(List<String> toolkits) {
    this.toolkits = toolkits;
    return this;
  }

  public ExerciseBuilder withOwner(String owner) {
    this.owner = owner;
    return this;
  }

  public Exercise build() {
    Exercise exercise = new Exercise();
    exercise.setId(id);
    exercise.setName(name);
    exercise.setDescription(description);
    exercise.setPictureUrl(pictureUrl);
//    exercise.setDevices(devices);
//    exercise.setToolkits(toolkits);
    exercise.setOwner(owner);
    return exercise;
  }
}
