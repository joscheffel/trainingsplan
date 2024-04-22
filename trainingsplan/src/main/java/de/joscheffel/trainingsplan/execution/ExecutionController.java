package de.joscheffel.trainingsplan.execution;

import de.joscheffel.trainingsplan.execution.dtos.ExecutionRequestDto;
import de.joscheffel.trainingsplan.execution.dtos.ExecutionResponseDto;
import de.joscheffel.trainingsplan.generics.EntityRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executions")
public class ExecutionController extends
    EntityRestController<ExecutionRequestDto, ExecutionResponseDto, String> {

  protected ExecutionController(ExecutionService executionService) {
    super(executionService);
  }
}
