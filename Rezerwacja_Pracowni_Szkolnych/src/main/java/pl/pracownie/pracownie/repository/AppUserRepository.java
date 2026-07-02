package pl.pracownie.pracownie.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.pracownie.pracownie.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
