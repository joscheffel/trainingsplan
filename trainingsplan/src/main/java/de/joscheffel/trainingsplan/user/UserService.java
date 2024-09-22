package de.joscheffel.trainingsplan.user;

import de.joscheffel.trainingsplan.generics.EntityService;
import de.joscheffel.trainingsplan.user.dtos.KeycloakUserInfoDto;
import de.joscheffel.trainingsplan.user.dtos.UserRequestDto;
import de.joscheffel.trainingsplan.user.dtos.UserResponseDto;
import de.joscheffel.trainingsplan.utils.Response;

public interface UserService extends EntityService<UserRequestDto, UserResponseDto, String> {

  /**
   * Updates an existing User with the provided ID by the keycloak retrieved personal user
   * information.
   *
   * @param id                  The unique identifier of the User to be updated.
   * @param keycloakUserInfoDto The Users's Request DTO {@link KeycloakUserInfoDto} containing
   *                            updated data for the personal user information.
   * @return A {@link Response} containing the updated User's Response DTO {@link UserResponseDto}
   * if successful, otherwise an error response.
   */
  Response<UserResponseDto> updateUserInfo(String id, KeycloakUserInfoDto keycloakUserInfoDto);

  /**
   * Creates an User with the provided keycloak user information.
   *
   * @param keycloakUserInfoDto The User's keycloak DTO {@link KeycloakUserInfoDto} containing data
   *                            for the personal user information
   * @return containing the created User's Response DTO {@link UserResponseDto} if successful,
   * otherwise an error response.
   */
  Response<UserResponseDto> createWithUserInfo(KeycloakUserInfoDto keycloakUserInfoDto);
}
