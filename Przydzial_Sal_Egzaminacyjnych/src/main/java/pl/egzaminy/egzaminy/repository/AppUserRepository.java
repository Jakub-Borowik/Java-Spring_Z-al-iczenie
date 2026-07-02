package pl.egzaminy.egzaminy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.egzaminy.egzaminy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
