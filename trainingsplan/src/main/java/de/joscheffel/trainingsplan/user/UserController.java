package de.joscheffel.trainingsplan.user;

import de.joscheffel.trainingsplan.generics.EntityRestController;
import de.joscheffel.trainingsplan.user.dtos.KeycloakUserInfoDto;
import de.joscheffel.trainingsplan.user.dtos.UserRequestDto;
import de.joscheffel.trainingsplan.user.dtos.UserResponseDto;
import java.security.Principal;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends EntityRestController<UserRequestDto, UserResponseDto, String> {

  private final UserService userService;

  protected UserController(UserService userService) {
    super(userService);
    this.userService = userService;
  }

  private static KeycloakUserInfoDto retrieveKeycloakUserInfoDtoFrom(Principal principal) {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) principal;
    Jwt jwt = jwtAuthenticationToken.getToken();
    var sub = (String) jwt.getClaim(JwtClaimNames.SUB);
    var givenName = (String) jwt.getClaim("given_name");
    var familyName = (String) jwt.getClaim("family_name");

    return new KeycloakUserInfoDto(sub, givenName, familyName);
  }

  @PutMapping("/keycloakUserInfo/{userId}")
  public ResponseEntity<?> updateWithKeycloakInfo(@PathVariable String userId,
      Principal principal) {
    var keycloakUserInfoDto = retrieveKeycloakUserInfoDtoFrom(principal);

    if (Objects.nonNull(userId)) {
      var userResponse = userService.updateUserInfo(userId, keycloakUserInfoDto);

      if (Objects.nonNull(userResponse)) {
        return responseToResponseEntity(userResponse, HttpStatus.OK, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @PostMapping("/keycloakUserInfo")
  public ResponseEntity<?> createWithKeycloakInfo(Principal principal) {
    var keycloakUserInfoDto = retrieveKeycloakUserInfoDtoFrom(principal);

      var userResponse = userService.createWithUserInfo(keycloakUserInfoDto);

      if (Objects.nonNull(userResponse)) {
        return responseToResponseEntity(userResponse, HttpStatus.OK, HttpStatus.BAD_REQUEST);
      }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

}
