package de.joscheffel.trainingsplan.generics;

import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;

public interface AdvancedEntityService<TDtoRequest, TDtoResponse, TUserInformation, TId, TPermissionUserPair> {

  /**
   * Stores a new Entity based on the provided data.
   *
   * @param entityRequestDto The {@link TDtoRequest} containing data for the new Entity.
   * @param userInformation  The {@link TUserInformation} containing information about the user.
   * @return A {@link Response} containing the created Entity Response DTO{@link TDtoResponse} if
   * successful, otherwise an error response.
   */
  Response<TDtoResponse> store(TDtoRequest entityRequestDto, TUserInformation userInformation);

  /**
   * Retrieves Entity information based on the provided ID.
   *
   * @param id              The unique identifier of the Entity.
   * @param userInformation The {@link TUserInformation} containing information about the user.
   * @return A {@link Response} containing Entity's response DTO {@link TDtoResponse} if successful,
   * otherwise an error response.
   */
  Response<TDtoResponse> show(TId id, TUserInformation userInformation);

  /**
   * @param userInformation The {@link TUserInformation} containing information about the user.
   * @return A {@link Response} containing all Entity Response DTO {@link TDtoResponse} if
   * successful, otherwise an error response
   */
  Response<List<TDtoResponse>> showAll(TUserInformation userInformation);

  /**
   * Updates an existing Entity with the provided ID.
   *
   * @param id               The unique identifier of the Entity to be updated.
   * @param entityRequestDto The Entity's Request DTO {@link TDtoRequest} containing updated data.
   * @param userInformation  The {@link TUserInformation} containing information about the user.
   * @return A {@link Response} containing the updated Entity's Response DTO {@link TDtoResponse} if
   * successful, otherwise an error response.
   */
  Response<TDtoResponse> update(TId id, TDtoRequest entityRequestDto,
      TUserInformation userInformation);

  /**
   * Deletes the Entity with the provided ID.
   *
   * @param entityId        The unique identifier of the Entity to be deleted.
   * @param userInformation The {@link TUserInformation} containing information about the user.
   * @return A {@link Response} containing the deleted Entity's Response DTO {@link TDtoResponse} if
   * successful otherwise an error response.
   */
  Response<TDtoResponse> deleteById(TId entityId, TUserInformation userInformation);


  /**
   * Add a Permission for a specified user to the resource with the entity Id
   *
   * @param entityId           The unique Identifier of the Entity where a permission is added
   * @param userInformation    The {@link TUserInformation} containing information about the
   *                           requesting user.
   * @param permissionUserPair The {@link TPermissionUserPair} containing the user info and its
   *                           granted permission.
   * @return A {@link Response} containing the {@link Boolean} value true if successful, or an error
   * message otherwise
   */
  Response<Boolean> addPermission(TId entityId, TUserInformation userInformation,
      TPermissionUserPair permissionUserPair);


  /**
   * Remove a Permission from a specific user for the resource with the entity Id
   *
   * @param entityId           The unique Identifier of the Entity where a permission is removed
   * @param userInformation    The {@link TUserInformation} containing the information about the
   *                           requestion user.
   * @param permissionUserPair The {@link TPermissionUserPair} containing the user info and the
   *                           permission to be removed
   * @return A {@link Response} containing the {@link Boolean} value true if successful, or an error
   * message otherwise
   */
  Response<Boolean> removePermission(TId entityId, TUserInformation userInformation,
      TPermissionUserPair permissionUserPair);
}
