package pl.diety.diety.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.diety.diety.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
