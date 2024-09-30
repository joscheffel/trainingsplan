package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.ExerciseRepository;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.generics.KeyValuePair;
import de.joscheffel.trainingsplan.resource_access_control.EntityTypes;
import de.joscheffel.trainingsplan.resource_access_control.Permission;
import de.joscheffel.trainingsplan.resource_access_control.PermissionService;
import de.joscheffel.trainingsplan.resource_access_control.PermissionType;
import de.joscheffel.trainingsplan.resource_access_control.Resource;
import de.joscheffel.trainingsplan.resource_access_control.ResourceRepository;
import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VariationServiceImpl implements VariationService {

  public static String ERROR_COULDNT_FIND_REFERENCED_EXERCISE = "Couldn't find referenced exercise";
  public static String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private final VariationRepository variationRepository;
  private final VariationMapper variationMapper;
  private final ExerciseRepository exerciseRepository;
  private final ResourceRepository resourceRepository;
  private final PermissionService permissionService;

  public VariationServiceImpl(VariationRepository variationRepository,
      VariationMapper variationMapper, ExerciseRepository exerciseRepository,
      ResourceRepository resourceRepository, PermissionService permissionService) {
    this.variationRepository = variationRepository;
    this.variationMapper = variationMapper;
    this.exerciseRepository = exerciseRepository;
    this.resourceRepository = resourceRepository;
    this.permissionService = permissionService;
  }

  @Override
  public Response<VariationResponseDto> store(VariationRequestDto variationRequestDto,
      User requestingUser) {
    var exerciseOptional = exerciseRepository.findById(variationRequestDto.exerciseId());
    if (exerciseOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE);
    }
    var variation = variationMapper.mapVariationRequestDtoToVariation(variationRequestDto);
    variation.setExercise(exerciseOptional.get());
    var storedVariation = variationRepository.save(variation);

    // Create Resource for this Exercise
    Resource resource = new Resource();
    resource.setEntityId(storedVariation.getId());
    resource.setEntityType(EntityTypes.EXERCISE_VARIATION.name());
    resourceRepository.save(resource);

    storedVariation.setResource(resource);
    variationRepository.save(storedVariation);

    // Assign Ownership Permission
    permissionService.addPermission(requestingUser, resource, PermissionType.OWNER);

    if (variationRepository.existsById(storedVariation.getId())) {
      var variationResponseDto = variationMapper.mapEntityAndAccessToEntityResponseDto(
          storedVariation,
          permissionService.retrieveAccessControlFrom(storedVariation.getResource()));
      return Response.of(variationResponseDto);
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationResponseDto> show(String id, User requestingUser) {
    var permissionOptional = permissionService.retrievePermissionForUserAndEntityTypeAndEntityId(
        requestingUser, EntityTypes.EXERCISE_VARIATION, id);

    if (Objects.nonNull(id) && StringUtils.hasText(id) && permissionOptional.isPresent()) {
      var variationOptional = variationRepository.findByIdAndResource(id,
          permissionOptional.get().getResource());
      if (variationOptional.isPresent()) {
        var variationResponseDto = variationMapper.mapEntityAndAccessToEntityResponseDto(
            variationOptional.get(),
            permissionService.retrieveAccessControlFrom(variationOptional.get().getResource()));
        return Response.of(variationResponseDto);
      }
      return Response.error("Not Found");
    } else if (permissionOptional.isEmpty()) {
      return Response.error("Resource couldn't be found or isn't allowed");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<VariationResponseDto>> showAll(User requestingUser) {
    var list = permissionService.retrievePermissionsForUserAndResourceEntityType(requestingUser,
        EntityTypes.EXERCISE_VARIATION);

    var variations = variationRepository.findAllByResourceIn(
        list.stream().map(Permission::getResource).toList());

    var variationResponseDtos = variations.stream().map(
        variation -> variationMapper.mapEntityAndAccessToEntityResponseDto(variation,
            permissionService.retrieveAccessControlFrom(variation.getResource()))).toList();
    return Response.of(variationResponseDtos);
  }

  @Override
  public Response<VariationResponseDto> update(String id, VariationRequestDto variationRequestDto,
      User requestingUser) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (variationRepository.existsById(id)) {
        var exerciseOptional = exerciseRepository.findById(variationRequestDto.exerciseId());
        if (exerciseOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE);
        }
        var variation = variationMapper.mapVariationRequestDtoToVariation(variationRequestDto);
        variation.setExercise(exerciseOptional.get());
        variation.setId(id);

        // make sure the resource object stays the same
        var existingVariation = variationRepository.findById(id);
        if (existingVariation.isEmpty()) {
          return Response.error("Not Found");
        }
        variation.setResource(existingVariation.get().getResource());

        var storedVariation = variationRepository.save(variation);

        var variationResponseDto = variationMapper.mapEntityAndAccessToEntityResponseDto(
            storedVariation,
            permissionService.retrieveAccessControlFrom(storedVariation.getResource()));
        return Response.of(variationResponseDto);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationResponseDto> deleteById(String id, User requestingUser) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var variationOptional = variationRepository.findById(id);
      if (variationOptional.isPresent()) {
        variationRepository.deleteById(id);
        if (!variationRepository.existsById(id)) {
          var variationResponseDto = variationMapper.mapEntityAndAccessToEntityResponseDto(
              variationOptional.get(),
              permissionService.retrieveAccessControlFrom(variationOptional.get().getResource()));
          return Response.of(variationResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<List<VariationResponseDto>> showAllForExerciseId(String exerciseId,
      User requestingUser) {
    var variations = variationRepository.findAllByExerciseId(exerciseId);
    if (!variations.isEmpty()) {
      var resource = variations.stream().findFirst().get().getResource();
      var variationResponseDtos = variations.stream().map(
          variation -> variationMapper.mapEntityAndAccessToEntityResponseDto(variation,
              permissionService.retrieveAccessControlFrom(resource))).toList();
      return Response.of(variationResponseDtos);
    }
    return Response.error("Not Found");
  }

  @Override
  public Response<Boolean> addPermission(String entityId, User user,
      KeyValuePair<String, String> userIdPermissionTypeKeyValuePair) {
    return permissionService.addPermissionByEntityIdAndType(entityId,
        EntityTypes.EXERCISE_VARIATION, userIdPermissionTypeKeyValuePair.key(),
        PermissionType.fromValue(userIdPermissionTypeKeyValuePair.value()));
  }

  @Override
  public Response<Boolean> removePermission(String entityId, User user,
      KeyValuePair<String, String> userIdPermissionTypeKeyValuePair) {
    return permissionService.removePermissionByEntityIdAndType(entityId,
        EntityTypes.EXERCISE_VARIATION, userIdPermissionTypeKeyValuePair.key(),
        PermissionType.fromValue(userIdPermissionTypeKeyValuePair.value()));
  }
}
