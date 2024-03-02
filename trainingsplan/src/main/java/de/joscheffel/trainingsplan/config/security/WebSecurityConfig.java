package de.joscheffel.trainingsplan.config.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  public static final String ADMIN = "admin";
  public static final String USER = "user";
  public static final String ALL = "*";
  private final JwtAuthConverter jwtAuthConverter;

  public WebSecurityConfig(JwtAuthConverter jwtAuthConverter) {
    this.jwtAuthConverter = jwtAuthConverter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
            .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).hasAnyRole(USER, ADMIN)
            .anyRequest().authenticated());
    http.oauth2ResourceServer(
        httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(
            jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)));
    http.sessionManagement(
        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));
    http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
        corsConfigurationSource()));
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(ALL));
    configuration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Length", "Cache-Control", "Content-Type", "*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
