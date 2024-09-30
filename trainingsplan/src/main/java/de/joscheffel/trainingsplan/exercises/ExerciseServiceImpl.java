package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.exercises.mapper.ExerciseMapper;
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
public class ExerciseServiceImpl implements ExerciseService {

  private final ExerciseRepository exerciseRepository;
  private final ExerciseMapper exerciseMapper;
  private final ResourceRepository resourceRepository;
  private final PermissionService permissionService;

  public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper,
      ResourceRepository resourceRepository, PermissionService permissionService) {
    this.exerciseRepository = exerciseRepository;
    this.exerciseMapper = exerciseMapper;
    this.resourceRepository = resourceRepository;
    this.permissionService = permissionService;
  }

  // ToDo: move access control from service layer to controller layer, since here it makes everything very complex!
  @Override
  public Response<ExerciseResponseDto> show(String id, User requestingUser) {
    var permissionOptional = permissionService.retrievePermissionForUserAndEntityTypeAndEntityId(
        requestingUser, EntityTypes.EXERCISE, id);

    if (Objects.nonNull(id) && StringUtils.hasText(id) && permissionOptional.isPresent()) {
      var exerciseOptional = exerciseRepository.findByIdAndResource(id,
          permissionOptional.get().getResource());
      if (exerciseOptional.isPresent()) {
        var exerciseResponseDto = exerciseMapper.mapEntityAndAccessToEntityResponseDto(
            exerciseOptional.get(),
            permissionService.retrieveAccessControlFrom(exerciseOptional.get().getResource()));
        return Response.of(exerciseResponseDto);
      }
      return Response.error("Not Found");
    } else if (permissionOptional.isEmpty()) {
      return Response.error("Resource couldn't be found or isn't allowed");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<ExerciseResponseDto>> showAll(User requestingUser) {
    var list = permissionService.retrievePermissionsForUserAndResourceEntityType(requestingUser,
        EntityTypes.EXERCISE);
    var exercises = exerciseRepository.findAllByResourceIn(
        list.stream().map(Permission::getResource).toList());
    var exerciseResponseDtos = exercises.stream().map(
        exercise -> exerciseMapper.mapEntityAndAccessToEntityResponseDto(exercise,
            permissionService.retrieveAccessControlFrom(exercise.getResource()))).toList();
    return Response.of(exerciseResponseDtos);
  }

  @Override
  public Response<ExerciseResponseDto> store(ExerciseRequestDto exerciseRequestDto,
      User requestingUser) {
    var exercise = exerciseMapper.mapExerciseRequestDtoToExercise(exerciseRequestDto);
    var storedExercise = exerciseRepository.save(exercise);

    // Create Resource for this Exercise
    Resource resource = new Resource();
    resource.setEntityId(storedExercise.getId());
    resource.setEntityType(EntityTypes.EXERCISE.name());
    resourceRepository.save(resource);

    storedExercise.setResource(resource);
    exerciseRepository.save(storedExercise);

    // Assign Ownership Permission
    permissionService.addPermission(requestingUser, resource, PermissionType.OWNER);

    if (exerciseRepository.existsById(storedExercise.getId())) {
      var exerciseResponseDto = exerciseMapper.mapEntityAndAccessToEntityResponseDto(exercise,
          permissionService.retrieveAccessControlFrom(exercise.getResource()));
      return Response.of(exerciseResponseDto);
    }
    return Response.error("Failed to store the exercise");
  }

  @Override
  public Response<ExerciseResponseDto> update(String id, ExerciseRequestDto exerciseRequestDto,
      User requestingUser) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (exerciseRepository.existsById(id)) {
        var exercise = exerciseMapper.mapExerciseRequestDtoToExercise(exerciseRequestDto);
        exercise.setId(id);

        // make sure the resource object stays the same
        var existingExercise = exerciseRepository.findById(id);
        if (existingExercise.isEmpty()) {
          return Response.error("Not Found");
        }
        exercise.setResource(existingExercise.get().getResource());

        var storedExercise = exerciseRepository.save(exercise);

        var exerciseResponseDto = exerciseMapper.mapEntityAndAccessToEntityResponseDto(storedExercise,
            permissionService.retrieveAccessControlFrom(storedExercise.getResource()));
        return Response.of(exerciseResponseDto);
      }
    }
    return Response.error("Failed to update the exercise");
  }

  // ToDo: delete permissions and Resource to! Maybe parts with cascade already done?
  @Override
  public Response<ExerciseResponseDto> deleteById(String id, User requestingUser) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var exerciseOptional = exerciseRepository.findById(id);
      if (exerciseOptional.isPresent()) {
        exerciseRepository.deleteById(id);
        if (!exerciseRepository.existsById(id)) {
          var exerciseResponseDto = exerciseMapper.mapEntityAndAccessToEntityResponseDto(
              exerciseOptional.get(),
              permissionService.retrieveAccessControlFrom(exerciseOptional.get().getResource()));
          return Response.of(exerciseResponseDto);
        }
      }
    }
    return Response.error("Failed to delete the exercise");
  }

  @Override
  public Response<Boolean> addPermission(String entityId, User user,
      KeyValuePair<String, String> userIdPermissionTypeKeyValuePair) {
    return permissionService.addPermissionByEntityIdAndType(entityId, EntityTypes.EXERCISE,
        userIdPermissionTypeKeyValuePair.key(),
        PermissionType.fromValue(userIdPermissionTypeKeyValuePair.value()));
  }

  @Override
  public Response<Boolean> removePermission(String entityId, User user,
      KeyValuePair<String, String> userIdPermissionTypeKeyValuePair) {
    return permissionService.removePermissionByEntityIdAndType(entityId, EntityTypes.EXERCISE,
        userIdPermissionTypeKeyValuePair.key(),
        PermissionType.fromValue(userIdPermissionTypeKeyValuePair.value()));
  }
}