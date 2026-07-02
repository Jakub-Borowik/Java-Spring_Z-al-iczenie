package pl.transportmed.transportmed.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.transportmed.transportmed.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
