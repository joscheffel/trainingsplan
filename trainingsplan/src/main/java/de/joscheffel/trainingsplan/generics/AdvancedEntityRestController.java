package de.joscheffel.trainingsplan.generics;

import de.joscheffel.trainingsplan.utils.Response;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class AdvancedEntityRestController<TDtoRequest, TDtoResponse, TUserInformation, TId, TPermissionUserPair> {

  public static final String ERROR_OPERATION_FAILURE = "Operation failure";
  public static final String SUCCESS_ENTITY_DELETION = "Successfully deleted entity with id %s";

  private final AdvancedEntityService<TDtoRequest, TDtoResponse, TUserInformation, TId, TPermissionUserPair> entityService;

  protected AdvancedEntityRestController(
      AdvancedEntityService<TDtoRequest, TDtoResponse, TUserInformation, TId, TPermissionUserPair> entityService) {
    this.entityService = entityService;
  }

  public static ResponseEntity<?> responseToResponseEntity(Response<?> response, HttpStatus success,
      HttpStatus error) {
    return response.isError() ? ResponseEntity.status(error).body(response.errorMessage())
        : ResponseEntity.status(success).body(response.entity());
  }

  public abstract Response<TUserInformation> retrieveUserInformation(Principal principal);

  @GetMapping
  public ResponseEntity<?> findAll(Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (!userInformation.isError()) {
      var entityResponses = entityService.showAll(userInformation.entity());

      if (Objects.nonNull(entityResponses)) {
        return responseToResponseEntity(entityResponses, HttpStatus.OK, HttpStatus.NO_CONTENT);
      }
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @GetMapping("/{entityId}")
  public ResponseEntity<?> findEntityById(@PathVariable TId entityId, Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityId) && !userInformation.isError()) {
      var entityResponse = entityService.show(entityId, userInformation.entity());

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.OK, HttpStatus.NOT_FOUND);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  // ToDo: Check whether provided owner equals given owner
  @PostMapping
  public ResponseEntity<?> createEntity(@Valid @RequestBody TDtoRequest entityRequestDto,
      Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityRequestDto) && !userInformation.isError()) {
      var entityResponse = entityService.store(entityRequestDto, userInformation.entity());

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @PutMapping("/{entityId}")
  public ResponseEntity<?> updateEntity(@PathVariable TId entityId,
      @Valid @RequestBody TDtoRequest entityRequestDto, Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityId) && Objects.nonNull(entityRequestDto)
        && !userInformation.isError()) {
      var entityResponse = entityService.update(entityId, entityRequestDto,
          userInformation.entity());

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.OK, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @DeleteMapping("/{entityId}")
  public ResponseEntity<?> deleteEntityById(@PathVariable TId entityId, Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityId) && !userInformation.isError()) {
      var entityResponse = entityService.deleteById(entityId, userInformation.entity());

      if (Objects.nonNull(entityResponse)) {
        return entityResponse.isError() ? ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(entityResponse.errorMessage())
            : ResponseEntity.status(HttpStatus.OK).body(entityResponse.entity());
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @PostMapping("/{entityId}/permissions")
  public ResponseEntity<?> addPermission(@PathVariable TId entityId,
      @RequestBody TPermissionUserPair permission, Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityId) && Objects.nonNull(permission) && !userInformation.isError()) {
      var entityResponse = entityService.addPermission(entityId, userInformation.entity(),
          permission);
      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @PutMapping("/{entityId}/permissions")
  public ResponseEntity<?> removePermission(@PathVariable TId entityId,
      @RequestBody TPermissionUserPair permission, Principal principal) {
    Response<TUserInformation> userInformation = retrieveUserInformation(principal);
    if (Objects.nonNull(entityId) && Objects.nonNull(permission) && !userInformation.isError()) {
      var entityResponse = entityService.removePermission(entityId, userInformation.entity(),
          permission);
      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }
}
