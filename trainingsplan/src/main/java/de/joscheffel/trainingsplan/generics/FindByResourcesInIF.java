package de.joscheffel.trainingsplan.generics;

import de.joscheffel.trainingsplan.resource_access_control.Resource;
import java.util.List;
import java.util.Optional;

public interface FindByResourcesInIF<TEntity, TId> {

  List<TEntity> findAllByResourceIn(List<Resource> resources);

  Optional<TEntity> findByIdAndResource(TId id, Resource resource);
}
