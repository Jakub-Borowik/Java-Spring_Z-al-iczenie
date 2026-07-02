package pl.coworking.coworking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coworking.coworking.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
