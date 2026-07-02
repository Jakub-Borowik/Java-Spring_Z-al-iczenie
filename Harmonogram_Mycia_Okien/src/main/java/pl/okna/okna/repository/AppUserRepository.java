package pl.okna.okna.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.okna.okna.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
