package de.joscheffel.trainingsplan.user;

import de.joscheffel.trainingsplan.user.model.User;
import de.joscheffel.trainingsplan.utils.Response;
import org.springframework.stereotype.Service;

@Service
public class InternalUserService {

  private final UserRepository userRepository;

  public InternalUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Response<User> retrieveUserById(String id) {
    var userOptional = userRepository.findById(id);
    return userOptional.map(Response::of)
        .orElseGet(() -> Response.error("User not found for given id"));
  }
}
