package de.joscheffel.trainingsplan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public class SecurityUtils {
    static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static Response<JwtAuthenticationToken> retrieveJwtAuthenticationTokenOfPrincipal(Principal principal) {
        try {
            var token = (JwtAuthenticationToken) principal;
            return Response.of(token);
        } catch (Exception ex) {
            logger.error("Casting principal to JwtAuthenticationToken went wrong: {}", ex.getMessage());
        }
        return Response.error("Casting went wrong");
    }
}
