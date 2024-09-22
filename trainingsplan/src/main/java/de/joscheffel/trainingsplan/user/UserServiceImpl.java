package de.joscheffel.trainingsplan.user;

import de.joscheffel.trainingsplan.user.dtos.KeycloakUserInfoDto;
import de.joscheffel.trainingsplan.user.dtos.UserRequestDto;
import de.joscheffel.trainingsplan.user.dtos.UserResponseDto;
import de.joscheffel.trainingsplan.user.mapper.UserMapper;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

  private static final String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private static final String ERROR_USER_WITH_THIS_KEYCLOAK_ID_ALREADY_EXISTS = "User with this keycloak id already exists";
  private static final String ERROR_NOT_SUPPORTED_YET = "Not supported yet";
  private static final String ERROR_KEYCLOAK_ID_DOES_NOT_MATCH = "Keycloak id does not match";
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
  }

  @Override
  public Response<UserResponseDto> store(UserRequestDto entityRequestDto) {

    return Response.error(ERROR_NOT_SUPPORTED_YET);
  }

  @Override
  public Response<UserResponseDto> show(String id) {
    return Response.error(ERROR_NOT_SUPPORTED_YET);
//    var user = userRepository.findById(id);
//
//    return user.map(value -> Response.of(userMapper.mapUserToUserResponseDto(value)))
//        .orElseGet(() -> Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION));
  }

  @Override
  public Response<List<UserResponseDto>> showAll() {
    return Response.error(ERROR_NOT_SUPPORTED_YET);
//    var users = userRepository.findAll();
//    var userResponseDtos = users.stream().map(userMapper::mapUserToUserResponseDto).toList();
//    return Response.of(userResponseDtos);
  }

  @Override
  public Response<UserResponseDto> update(String id, UserRequestDto userRequestDto) {
    var existingUser = userRepository.findById(id);

    if (existingUser.isPresent()) {
      var updatedUser = userMapper.mapDatabaseUserAndUserRequestDtoToUser(userRequestDto,
          existingUser.get());
      var user = userRepository.save(updatedUser);

      return Response.of(userMapper.mapUserToUserResponseDto(user));
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<UserResponseDto> deleteById(String userId) {
    if (Objects.nonNull(userId) && StringUtils.hasText(userId)) {
      var userOptional = userRepository.findById(userId);
      if (userOptional.isPresent()) {
        userRepository.deleteById(userId);
        if (!userRepository.existsById(userId)) {

          var userResponseDto = userMapper.mapUserToUserResponseDto(userOptional.get());
          return Response.of(userResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<UserResponseDto> updateUserInfo(String id,
      KeycloakUserInfoDto keycloakUserInfoDto) {
    var existingUser = userRepository.findById(id);
    if (existingUser.isPresent()) {
      if (existingUser.get().getKeycloakUserId().equals(keycloakUserInfoDto.keycloakUserId())) {
        var user = userMapper.mapKeycloakUserInfoDtoAndExistingUserToUser(keycloakUserInfoDto,
            existingUser.get());
        var updatedUser = userRepository.save(user);

        var userResponseDto = userMapper.mapUserToUserResponseDto(updatedUser);
        return Response.of(userResponseDto);
      } else {
        return Response.error(ERROR_KEYCLOAK_ID_DOES_NOT_MATCH);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<UserResponseDto> createWithUserInfo(KeycloakUserInfoDto keycloakUserInfoDto) {
    var user = userMapper.mapKeycloakUserInfoDtoToUser(keycloakUserInfoDto);

    if (!userRepository.existsUserByKeycloakUserId(user.getKeycloakUserId())) {
      var createdUser = userRepository.save(user);

      if (userRepository.existsById(createdUser.getId())) {
        var userResponseDto = userMapper.mapUserToUserResponseDto(createdUser);
        return Response.of(userResponseDto);
      }
    }

    return Response.error(ERROR_USER_WITH_THIS_KEYCLOAK_ID_ALREADY_EXISTS);
  }
}
