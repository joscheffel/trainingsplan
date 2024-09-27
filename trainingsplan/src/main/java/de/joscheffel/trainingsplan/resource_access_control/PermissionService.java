package de.joscheffel.trainingsplan.resource_access_control;

import de.joscheffel.trainingsplan.exercises.ExerciseRepository;
import de.joscheffel.trainingsplan.exercises.variations.VariationRepository;
import de.joscheffel.trainingsplan.plan.PlanRepository;
import de.joscheffel.trainingsplan.user.InternalUserService;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

  private static final String ERROR_ADDING_PERMISSION = "Something went wrong while adding the Permission";
  private static final String ERROR_REMOVING_PERMISSION = "Something went wrong while removing the Permission";
  private static final String ERROR_FINDING_PERMISSION = "Something went wrong while finding the Permission";
  private static final String ERROR_FINDING_RESOURCE = "Couldn't find resource";

  private final PermissionRepository permissionRepository;
  private final InternalUserService internalUserService;
  private final PlanRepository planRepository;
  private final ExerciseRepository exerciseRepository;
  private final VariationRepository variationRepository;
  private final ResourceRepository resourceRepository;

  public PermissionService(PermissionRepository permissionRepository,
      InternalUserService internalUserService, PlanRepository planRepository,
      ExerciseRepository exerciseRepository, VariationRepository variationRepository,
      ResourceRepository resourceRepository) {
    this.permissionRepository = permissionRepository;
    this.internalUserService = internalUserService;
    this.planRepository = planRepository;
    this.exerciseRepository = exerciseRepository;
    this.variationRepository = variationRepository;
    this.resourceRepository = resourceRepository;
  }

  public boolean isOwner(User user, Resource resource) {
    return permissionRepository.existsByUserAndResourceAndPermissionType(user, resource,
        PermissionType.OWNER);
  }

  public boolean canEdit(User user, Resource resource) {
    return permissionRepository.existsByUserAndResourceAndPermissionType(user, resource,
        PermissionType.EDITOR) || isOwner(user, resource);
  }

  public boolean canView(User user, Resource resource) {
    return permissionRepository.existsByUserAndResourceAndPermissionType(user, resource,
        PermissionType.VIEWER) || isOwner(user, resource) || canEdit(user, resource);
  }

  public boolean canDelete(User user, Resource resource) {
    return isOwner(user, resource);
  }

  public Response<Boolean> addPermission(User user, Resource resource,
      PermissionType permissionType) {
    Permission permission = new Permission();
    permission.setUser(user);
    permission.setResource(resource);
    permission.setPermissionType(permissionType);
    permissionRepository.save(permission);
    if (permissionRepository.existsByUserAndResourceAndPermissionType(user, resource,
        permissionType)) {
      return Response.of(true);
    }
    return Response.error(ERROR_ADDING_PERMISSION);
  }

  public Response<Boolean> addPermissionByEntityIdAndType(String entityId, EntityTypes entityType,
      String userId, PermissionType permissionType) {
    var resourceOptional = resourceRepository.findResourceByEntityIdAndEntityType(entityId,
        entityType.name());
    var userResponse = internalUserService.retrieveUserById(userId);
    if (resourceOptional.isPresent() && !userResponse.isError()) {
      return addPermission(userResponse.entity(), resourceOptional.get(), permissionType);
    }
    return Response.error(ERROR_FINDING_RESOURCE);
  }

  public Response<Boolean> removePermission(User user, Resource resource,
      PermissionType permissionType) {
    var permissionOptional = permissionRepository.findPermissionByUserAndResourceAndPermissionType(
        user, resource, permissionType);
    if (permissionOptional.isPresent()) {
      permissionRepository.delete(permissionOptional.get());
      if (!permissionRepository.existsByUserAndResourceAndPermissionType(user, resource,
          permissionType)) {
        return Response.of(true);
      }
      return Response.error(ERROR_REMOVING_PERMISSION);
    }
    return Response.error(ERROR_FINDING_PERMISSION);
  }

  public Response<Boolean> removePermissionByEntityIdAndType(String entityId,
      EntityTypes entityType, String userId, PermissionType permissionType) {
    var resourceOptional = resourceRepository.findResourceByEntityIdAndEntityType(entityId,
        entityType.name());
    var userResponse = internalUserService.retrieveUserById(userId);
    if (resourceOptional.isPresent() && !userResponse.isError()) {
      return removePermission(userResponse.entity(), resourceOptional.get(), permissionType);
    }
    return Response.error(ERROR_FINDING_RESOURCE);
  }

  public List<AccessControlWrapper> retrieveAccessControlFrom(Resource resource) {
    var permissions = permissionRepository.findAllByResource(resource);
    var permissionsByUser = permissions.stream().collect(Collectors.groupingBy(Permission::getUser,
        Collectors.mapping(Permission::getPermissionType, Collectors.toList())));
    return permissionsByUser.entrySet().stream().map(
        entry -> new AccessControlWrapper(entry.getKey().getId(), entry.getKey().getPseudonym(),
            entry.getValue())).toList();
  }

  public List<Permission> retrievePermissionsForUserAndResourceEntityType(User user,
      EntityTypes entityType) {
    return permissionRepository.findAllByUserAndResourceEntityType(user, entityType.name());
  }

  public Optional<Permission> retrievePermissionForUserAndEntityTypeAndEntityId(User user, EntityTypes entityType, String entityId) {
    var resourceOptional = resourceRepository.findResourceByEntityIdAndEntityType(entityId, entityType.name());
    if (resourceOptional.isPresent()) {
      return permissionRepository.findPermissionByUserAndResource(user, resourceOptional.get());
    }
    return Optional.empty();
  }
}
