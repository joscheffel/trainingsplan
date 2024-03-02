package de.joscheffel.trainingsplan.exercises.dtos;

import de.joscheffel.trainingsplan.devices.dtos.DeviceResponseDto;
import java.util.List;

public record ExerciseRequestDto(String name, String description, String pictureUrl,
                                 List<DeviceResponseDto> devices, List<String> toolkits,
                                 String owner) {

}
