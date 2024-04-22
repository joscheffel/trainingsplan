package de.joscheffel.trainingsplan.devices;

import de.joscheffel.trainingsplan.devices.dtos.DeviceRequestDto;
import de.joscheffel.trainingsplan.devices.dtos.DeviceResponseDto;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface DeviceService {

  /**
   * Retrieves device information based on the provided ID.
   *
   * @param id The unique identifier of the device.
   * @return A {@link Response} containing {@link DeviceResponseDto} if successful, otherwise an
   * error response.
   */
  Response<DeviceResponseDto> show(String id);

  /**
   * Retrieves all devices owned by the provided owner.
   *
   * @param owner The owner for which all devices should be retrieved.
   * @return A {@link Response} containing a list of {@link DeviceResponseDto} if successful,
   * otherwise an error response.
   */
  Response<List<DeviceResponseDto>> showAllByOwner(String owner);

  /**
   * Stores a new device based on the provided data.
   *
   * @param deviceRequestDto The {@link DeviceRequestDto} containing data for the new device.
   * @return A {@link Response} containing the created {@link DeviceResponseDto} if successful,
   * otherwise an error response.
   */
  Response<DeviceResponseDto> store(DeviceRequestDto deviceRequestDto);

  /**
   * Updates an existing device with the provided ID.
   *
   * @param id               The unique identifier of the device to be updated.
   * @param deviceRequestDto The {@link DeviceRequestDto} containing updated data.
   * @return A {@link Response} containing the updated {@link DeviceResponseDto} if successful,
   * otherwise an error response.
   */
  Response<DeviceResponseDto> update(String id, DeviceRequestDto deviceRequestDto);

  /**
   * Deletes the device with the provided ID.
   *
   * @param id The unique identifier of the device to be deleted.
   * @return A {@link Response} containing the deleted {@link DeviceResponseDto} if successful,
   * otherwise an error response.
   */
  Response<DeviceResponseDto> deleteById(String id);

}
