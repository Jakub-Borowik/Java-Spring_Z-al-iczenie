package pl.magazyn.magazyn.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.magazyn.magazyn.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
