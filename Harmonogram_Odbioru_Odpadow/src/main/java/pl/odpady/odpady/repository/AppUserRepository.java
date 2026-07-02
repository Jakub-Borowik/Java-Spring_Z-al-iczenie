package pl.odpady.odpady.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.odpady.odpady.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
