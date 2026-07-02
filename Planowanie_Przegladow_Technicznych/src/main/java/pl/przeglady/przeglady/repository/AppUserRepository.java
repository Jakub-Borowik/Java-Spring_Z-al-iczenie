package pl.przeglady.przeglady.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.przeglady.przeglady.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
