package pl.konkutery.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konkutery.helpdesk.model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
