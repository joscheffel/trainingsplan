package de.joscheffel.trainingsplan.generics;

import de.joscheffel.trainingsplan.utils.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class EntityRestController<TDtoRequest, TDtoResponse, TId> {

  // ToDo: Add Owner validation, so that only owner can change data
  public static final String ERROR_OPERATION_FAILURE = "Operation failure";
  public static final String SUCCESS_ENTITY_DELETION = "Successfully deleted entity with id %s";

  private final EntityService<TDtoRequest, TDtoResponse, TId> entityService;

  protected EntityRestController(EntityService<TDtoRequest, TDtoResponse, TId> entityService) {
    this.entityService = entityService;
  }

  public static ResponseEntity<?> responseToResponseEntity(Response<?> response, HttpStatus success,
      HttpStatus error) {
    return response.isError() ? ResponseEntity.status(error).body(response.errorMessage())
        : ResponseEntity.status(success).body(response.entity());
  }

  @GetMapping
  public ResponseEntity<?> findAll() {
    var entityResponses = entityService.showAll();

    if (Objects.nonNull(entityResponses)) {
      return responseToResponseEntity(entityResponses, HttpStatus.OK, HttpStatus.NO_CONTENT);
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @GetMapping("/{entityId}")
  public ResponseEntity<?> findEntityById(@PathVariable TId entityId) {
    if (Objects.nonNull(entityId)) {
      var entityResponse = entityService.show(entityId);

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.OK, HttpStatus.NOT_FOUND);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  // ToDo: Check whether provided owner equals given owner
  @PostMapping
  public ResponseEntity<?> createEntity(@Valid @RequestBody TDtoRequest entityRequestDto) {
    if (Objects.nonNull(entityRequestDto)) {
      var entityResponse = entityService.store(entityRequestDto);

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @PutMapping("/{entityId}")
  public ResponseEntity<?> updateEntity(@PathVariable TId entityId,
      @Valid @RequestBody TDtoRequest entityRequestDto) {
    if (Objects.nonNull(entityId) && Objects.nonNull(entityRequestDto)) {
      var entityResponse = entityService.update(entityId, entityRequestDto);

      if (Objects.nonNull(entityResponse)) {
        return responseToResponseEntity(entityResponse, HttpStatus.OK, HttpStatus.BAD_REQUEST);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

  @DeleteMapping("/{entityId}")
  public ResponseEntity<?> deleteEntityById(@PathVariable TId entityId) {
    if (Objects.nonNull(entityId)) {
      var entityResponse = entityService.deleteById(entityId);

      if (Objects.nonNull(entityResponse)) {
        return entityResponse.isError() ? ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(entityResponse.errorMessage())
            : ResponseEntity.status(HttpStatus.OK).body(entityResponse.entity());
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }
}
