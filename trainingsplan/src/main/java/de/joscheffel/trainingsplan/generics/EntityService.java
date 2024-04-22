package de.joscheffel.trainingsplan.generics;

import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface EntityService<TDtoRequest, TDtoResponse, TId> {

  /**
   * Stores a new Entity based on the provided data.
   *
   * @param entityRequestDto The {@link TDtoRequest} containing data for the new Entity.
   * @return A {@link Response} containing the created Entity Response DTO{@link TDtoResponse} if
   * successful, otherwise an error response.
   */
  Response<TDtoResponse> store(TDtoRequest entityRequestDto);

  /**
   * Retrieves Entity information based on the provided ID.
   *
   * @param id The unique identifier of the Entity.
   * @return A {@link Response} containing Entity's response DTO {@link TDtoResponse} if successful,
   * otherwise an error response.
   */
  Response<TDtoResponse> show(TId id);

  Response<List<TDtoResponse>> showAll();

  /**
   * Updates an existing Entity with the provided ID.
   *
   * @param id               The unique identifier of the Entity to be updated.
   * @param entityRequestDto The Entity's Request DTO {@link TDtoRequest} containing updated data.
   * @return A {@link Response} containing the updated Entity's Response DTO {@link TDtoResponse} if
   * successful, otherwise an error response.
   */
  Response<TDtoResponse> update(TId id, TDtoRequest entityRequestDto);

  /**
   * Deletes the Entity with the provided ID.
   *
   * @param entityId The unique identifier of the Entity to be deleted.
   * @return A {@link Response} containing the deleted Entity's Response DTO {@link TDtoResponse} if
   * successful otherwise an error response.
   */
  Response<TDtoResponse> deleteById(TId entityId);
}
