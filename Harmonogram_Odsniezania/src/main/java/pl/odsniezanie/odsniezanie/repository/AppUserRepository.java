package pl.odsniezanie.odsniezanie.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.odsniezanie.odsniezanie.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
