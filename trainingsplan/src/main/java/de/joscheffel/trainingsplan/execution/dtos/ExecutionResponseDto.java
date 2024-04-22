package de.joscheffel.trainingsplan.execution.dtos;

public record ExecutionResponseDto(String id, String variationId, String planId,
                                   int executionOrder) {

}
