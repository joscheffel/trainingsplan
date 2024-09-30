package de.joscheffel.trainingsplan.resource_access_control;

import java.util.List;

public record AccessControlWrapper(String userId, String pseudonym, String keycloakUserId,
                                   List<PermissionType> permissions) {

}
