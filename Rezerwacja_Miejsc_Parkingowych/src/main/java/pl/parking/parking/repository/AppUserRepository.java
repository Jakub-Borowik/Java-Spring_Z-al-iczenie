package pl.parking.parking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.parking.parking.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
