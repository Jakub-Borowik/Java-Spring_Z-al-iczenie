package pl.internet.internet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.internet.internet.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
