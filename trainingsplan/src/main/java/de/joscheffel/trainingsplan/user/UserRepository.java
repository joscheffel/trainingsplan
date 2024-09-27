package de.joscheffel.trainingsplan.user;

import de.joscheffel.trainingsplan.plan.model.Plan;
import de.joscheffel.trainingsplan.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  boolean existsUserByKeycloakUserId(String keycloakUserId);

  Optional<User> findUserByKeycloakUserId(String keycloakUserId);
}
