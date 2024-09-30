package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.generics.AdvancedEntityRestController;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.JwtUserInfoUtils;
import de.joscheffel.trainingsplan.utils.Response;
import java.security.Principal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variations")
public class VariationController extends
    AdvancedEntityRestController<VariationRequestDto, VariationResponseDto, User, String, KeyValuePair<String, String>> {

  private final JwtUserInfoUtils jwtUserInfoUtils;

  protected VariationController(VariationService variationService,
      JwtUserInfoUtils jwtUserInfoUtils) {
    super(variationService);
    this.jwtUserInfoUtils = jwtUserInfoUtils;
  }

  @Override
  public Response<User> retrieveUserInformation(Principal principal) {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) principal;
    Jwt jwt = jwtAuthenticationToken.getToken();
    return jwtUserInfoUtils.getUserFromToken(jwt);
  }
}
