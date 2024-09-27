package de.joscheffel.trainingsplan.utils;

import de.joscheffel.trainingsplan.user.UserRepository;
import de.joscheffel.trainingsplan.user.model.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;

@Component
public class JwtUserInfoUtils {

  private final static String ERROR_CONVERSION_FROM_JWT_TO_USER_NOT_POSSIBLE = "Conversion from jwt to user was not possible";
  private final UserRepository userRepository;

  public JwtUserInfoUtils(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Response<User> getUserFromToken(Jwt jwt) {
    String claimName = JwtClaimNames.SUB;
    var sub = (String) jwt.getClaim(claimName);
    var user = userRepository.findUserByKeycloakUserId(sub);
    return user.map(Response::of)
        .orElseGet(() -> Response.error(ERROR_CONVERSION_FROM_JWT_TO_USER_NOT_POSSIBLE));
  }

}
