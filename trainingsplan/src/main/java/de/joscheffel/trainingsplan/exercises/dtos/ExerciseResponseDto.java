package de.joscheffel.trainingsplan.exercises.dtos;

import de.joscheffel.trainingsplan.devices.dtos.DeviceResponseDto;
import de.joscheffel.trainingsplan.resource_access_control.AccessControlWrapper;
import java.util.List;

public record ExerciseResponseDto(String id, String name, String description, String pictureUrl,
                                  List<DeviceResponseDto> devices, List<String> toolkits,
                                  List<AccessControlWrapper> access) {

}
