package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.dtos.ExerciseRequestDto;
import de.joscheffel.trainingsplan.exercises.dtos.ExerciseResponseDto;
import de.joscheffel.trainingsplan.generics.EntityRestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController extends
    EntityRestController<ExerciseRequestDto, ExerciseResponseDto, String> {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    super(exerciseService);
    this.exerciseService = exerciseService;
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
