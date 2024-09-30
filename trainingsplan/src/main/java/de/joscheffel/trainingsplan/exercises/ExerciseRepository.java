package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.models.Exercise;
import de.joscheffel.trainingsplan.generics.FindByResourcesInIF;
import de.joscheffel.trainingsplan.resource_access_control.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExerciseRepository extends JpaRepository<Exercise, String>,
    FindByResourcesInIF<Exercise, String> {

}
