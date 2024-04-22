package de.joscheffel.trainingsplan.execution;

import de.joscheffel.trainingsplan.execution.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionRepository extends JpaRepository<Execution, String> {

}
