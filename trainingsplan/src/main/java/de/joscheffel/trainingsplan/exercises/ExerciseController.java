package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.exercises.variations.VariationService;
import de.joscheffel.trainingsplan.generics.EntityRestController;
import de.joscheffel.trainingsplan.utils.Response;
import java.security.Principal;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController extends
    EntityRestController<ExerciseRequestDto, ExerciseResponseDto, String> {

  private final VariationService variationService;

  public ExerciseController(ExerciseService exerciseService, VariationService variationService) {
    super(exerciseService);
    this.variationService = variationService;
  }

  @GetMapping("/{id}/variations")
  public ResponseEntity<?> getAllVariationsByExercise(Principal principal,
      @PathVariable String id) {
    if (Objects.nonNull(id)) {
      var variationServiceResponse = variationService.showAllForExerciseId(id);

      if (Objects.nonNull(variationServiceResponse)) {
        return responseToResponseEntity(variationServiceResponse, HttpStatus.OK,
            HttpStatus.NOT_FOUND);
      }
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_OPERATION_FAILURE);
  }

//  @GetMapping
//  public ResponseEntity<List<ExerciseResponseDto>> getAllExercisesOfOwner(Principal principal) {
//    var secResponse = SecurityUtils.retrieveJwtAuthenticationTokenOfPrincipal(principal);
//    if (!secResponse.isError()) {
//      var token = secResponse.entity();
//      var name = (String) token.getTokenAttributes().get("preferred_username");
//      var exerciseResponse = exerciseService.showAllForOwner(name);
//      if (!exerciseResponse.isError()) {
//        var exerciseResponseDtos = exerciseResponse.entity();
//        return new ResponseEntity<>(exerciseResponseDtos, HttpStatus.OK);
//      } else {
//        return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
//      }
//    }
//    return new ResponseEntity<>(List.of(), HttpStatus.BAD_REQUEST);
//  }

//    var secResponse = SecurityUtils.retrieveJwtAuthenticationTokenOfPrincipal(principal);
//    if (!secResponse.isError()) {
//      var token = secResponse.entity();

//  @PostMapping
//  public ResponseEntity<?> store(JwtAuthenticationToken principal,
//      @RequestBody ExerciseRequestDto exerciseRequestDto) {
//    var username = principal.getTokenAttributes().get("preferred_username");
//
//    if (Objects.nonNull(exerciseRequestDto) && exerciseRequestDto.owner().equals(username)) {
//      var entityResponse = exerciseService.store(exerciseRequestDto);
//
//      if (Objects.nonNull(entityResponse)) {
//        return responseToResponseEntity(entityResponse, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
//      }
//    }
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .body("Owner does not equal username.");
//  }
}
