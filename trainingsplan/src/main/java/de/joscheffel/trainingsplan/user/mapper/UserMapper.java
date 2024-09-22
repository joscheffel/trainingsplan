package de.joscheffel.trainingsplan.user.mapper;

import de.joscheffel.trainingsplan.user.dtos.KeycloakUserInfoDto;
import de.joscheffel.trainingsplan.user.dtos.UserRequestDto;
import de.joscheffel.trainingsplan.user.dtos.UserResponseDto;
import de.joscheffel.trainingsplan.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User mapKeycloakUserInfoDtoToUser(KeycloakUserInfoDto keycloakUserInfoDto);

  UserResponseDto mapUserToUserResponseDto(User user);

  @Mapping(target = "id", source = "user.id")
  @Mapping(target = "givenName", source = "keycloakUserInfoDto.givenName")
  @Mapping(target = "familyName", source = "keycloakUserInfoDto.familyName")
  @Mapping(target = "birthYear", source = "user.birthYear")
  @Mapping(target = "pseudonym", source = "user.pseudonym")
  @Mapping(target = "keycloakUserId", source = "keycloakUserInfoDto.keycloakUserId")
  User mapKeycloakUserInfoDtoAndExistingUserToUser(KeycloakUserInfoDto keycloakUserInfoDto, User user);

  User mapUserRequestDtoToUser(UserRequestDto userRequestDto);

  @Mapping(target = "id", source = "user.id")
  @Mapping(target = "givenName", source = "user.givenName")
  @Mapping(target = "familyName", source = "user.familyName")
  @Mapping(target = "birthYear", source = "userRequestDto.birthYear")
  @Mapping(target = "pseudonym", source = "userRequestDto.pseudonym")
  @Mapping(target = "keycloakUserId", source = "user.keycloakUserId")
  User mapDatabaseUserAndUserRequestDtoToUser(UserRequestDto userRequestDto, User user);
}
