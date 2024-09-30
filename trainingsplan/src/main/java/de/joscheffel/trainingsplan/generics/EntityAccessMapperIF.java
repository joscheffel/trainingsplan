package de.joscheffel.trainingsplan.generics;

import java.util.List;

public interface EntityAccessMapperIF<TEntity, TAccess, TEntityResponseDto> {

  TEntityResponseDto mapEntityAndAccessToEntityResponseDto(TEntity entity, List<TAccess> access);

}
