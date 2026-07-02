package pl.fotowoltaika.fotowoltaika.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.fotowoltaika.fotowoltaika.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
