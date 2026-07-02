package pl.klimatyzacja.klimatyzacja.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.klimatyzacja.klimatyzacja.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
