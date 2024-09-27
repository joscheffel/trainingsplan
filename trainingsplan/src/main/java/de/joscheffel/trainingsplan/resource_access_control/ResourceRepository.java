package de.joscheffel.trainingsplan.resource_access_control;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository  extends JpaRepository<Resource, String> {

  Optional<Resource> findResourceByEntityIdAndEntityType(String entityId, String entityType);

}
