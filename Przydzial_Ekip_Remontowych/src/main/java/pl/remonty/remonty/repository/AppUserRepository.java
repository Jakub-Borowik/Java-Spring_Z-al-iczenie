package pl.remonty.remonty.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.remonty.remonty.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
