package pl.ladowarki.ladowarki.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.ladowarki.ladowarki.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
