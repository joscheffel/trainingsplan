package de.joscheffel.trainingsplan.exercises;

import de.joscheffel.trainingsplan.exercises.models.Exercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExerciseRepository extends JpaRepository<Exercise, String> {
  List<Exercise> findAllByOwner(String owner);
}
