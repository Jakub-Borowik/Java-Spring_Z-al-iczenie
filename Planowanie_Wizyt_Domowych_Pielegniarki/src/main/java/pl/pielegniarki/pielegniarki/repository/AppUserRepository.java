package pl.pielegniarki.pielegniarki.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.pielegniarki.pielegniarki.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
