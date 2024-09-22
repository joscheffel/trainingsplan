package de.joscheffel.trainingsplan.user.dtos;

public record UserResponseDto(String id, String givenName, String familyName, Integer birthYear, String pseudonym, String keycloakUserId) {

}
