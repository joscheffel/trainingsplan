package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import de.joscheffel.trainingsplan.generics.FindByResourcesInIF;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, String>,
    FindByResourcesInIF<Variation, String> {

  List<Variation> findAllByExerciseId(String id);

}
