package de.joscheffel.trainingsplan.variationOrderPlan.dtos;

public record VariationOrderPlanResponseDto(String id, String variationId, String planId,
                                            int executionOrder) {

}
