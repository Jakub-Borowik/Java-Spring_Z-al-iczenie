package pl.naukajazdy.naukajazdy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.naukajazdy.naukajazdy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
