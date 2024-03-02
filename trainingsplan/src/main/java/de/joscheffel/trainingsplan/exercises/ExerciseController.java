package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.utils.SecurityUtils;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @GetMapping
  public ResponseEntity<List<ExerciseResponseDto>> getAllExercisesOfOwner(Principal principal) {
    var secResponse = SecurityUtils.retrieveJwtAuthenticationTokenOfPrincipal(principal);
    if (!secResponse.isError()) {
      var token = secResponse.entity();
      var name = (String) token.getTokenAttributes().get("preferred_username");
      var exerciseResponse = exerciseService.showAllForOwner(name);
      if (!exerciseResponse.isError()) {
        var exerciseResponseDtos = exerciseResponse.entity();
        return new ResponseEntity<>(exerciseResponseDtos, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
      }
    }
    return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExerciseResponseDto> getExerciseById(Principal principal,
      @PathVariable String id) {
    var secResponse = SecurityUtils.retrieveJwtAuthenticationTokenOfPrincipal(principal);
    if (!secResponse.isError()) {
      var token = secResponse.entity();
      if (Objects.nonNull(id)) {
        var exerciseResponse = exerciseService.show(id);
        if (!exerciseResponse.isError()) {
          var exerciseResponseDto = exerciseResponse.entity();
          return new ResponseEntity<>(exerciseResponseDto, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
      } else {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
    }
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping
  public ResponseEntity<ExerciseResponseDto> store(JwtAuthenticationToken principal,
      @RequestBody ExerciseRequestDto exerciseRequestDto) {
    var username = principal.getTokenAttributes().get("preferred_username");
    if (Objects.nonNull(exerciseRequestDto) && exerciseRequestDto.owner().equals(username)) {
      var exerciseResponse = exerciseService.store(exerciseRequestDto);
      if (!exerciseResponse.isError()) {
        var exerciseResponseDto = exerciseResponse.entity();
        return new ResponseEntity<>(exerciseResponseDto, HttpStatus.CREATED);
      }
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExerciseResponseDto> update(JwtAuthenticationToken principal,
      @RequestBody ExerciseRequestDto exerciseRequestDto, @PathVariable String id) {
    if (Objects.nonNull(exerciseRequestDto) && Objects.nonNull(id)
        && exerciseRequestDto.owner() == principal.getTokenAttributes().get("preferred_username")) {
      var exerciseResponse = exerciseService.update(id, exerciseRequestDto);
      if (!exerciseResponse.isError()) {
        var exerciseResponseDto = exerciseResponse.entity();
        return new ResponseEntity<>(exerciseResponseDto, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ExerciseResponseDto> delete(JwtAuthenticationToken principal,
      @PathVariable String id) {
    if (Objects.nonNull(id)) {
      var exerciseResponse = exerciseService.deleteById(id);
      if (!exerciseResponse.isError()) {
        var exerciseResponseDto = exerciseResponse.entity();
        return new ResponseEntity<>(exerciseResponseDto, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }
}
