package de.joscheffel.trainingsplan.resource_access_control;

import de.joscheffel.trainingsplan.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {

  boolean existsByUserAndResourceAndPermissionType(User user, Resource resource,
      PermissionType permissionType);

  List<Permission> findAllByResource(Resource resource);

  Optional<Permission> findPermissionByUserAndResourceAndPermissionType(User user, Resource resource, PermissionType permissionType);

  List<Permission> findAllByUserAndResourceEntityType(User user, String entityType);

  Optional<Permission> findPermissionByUserAndResource(User user, Resource resource);
}
