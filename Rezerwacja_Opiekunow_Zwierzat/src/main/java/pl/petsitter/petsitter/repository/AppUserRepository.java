package pl.petsitter.petsitter.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.petsitter.petsitter.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
