package de.joscheffel.trainingsplan.plan;

import de.joscheffel.trainingsplan.generics.AdvancedEntityRestController;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.plan.dtos.PlanRequestDto;
import de.joscheffel.trainingsplan.plan.dtos.PlanResponseDto;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.JwtUserInfoUtils;
import de.joscheffel.trainingsplan.utils.Response;
import java.security.Principal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
public class PlanController extends
    AdvancedEntityRestController<PlanRequestDto, PlanResponseDto, User, String, KeyValuePair<String, String>> {

  private final JwtUserInfoUtils jwtUserInfoUtils;

  protected PlanController(PlanService planService, JwtUserInfoUtils jwtUserInfoUtils) {
    super(planService);
    this.jwtUserInfoUtils = jwtUserInfoUtils;
  }

  @Override
  public Response<User> retrieveUserInformation(Principal principal) {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) principal;
    Jwt jwt = jwtAuthenticationToken.getToken();
    return jwtUserInfoUtils.getUserFromToken(jwt);
  }
}
