package pl.paczkomat.paczkomat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.paczkomat.paczkomat.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
