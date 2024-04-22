package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.model.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, String> {

}
