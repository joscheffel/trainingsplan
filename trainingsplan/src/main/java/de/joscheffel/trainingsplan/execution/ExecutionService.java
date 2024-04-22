package de.joscheffel.trainingsplan.execution;

import de.joscheffel.trainingsplan.execution.dtos.ExecutionRequestDto;
import de.joscheffel.trainingsplan.execution.dtos.ExecutionResponseDto;
import de.joscheffel.trainingsplan.generics.EntityService;

public interface ExecutionService extends
    EntityService<ExecutionRequestDto, ExecutionResponseDto, String> {

}
