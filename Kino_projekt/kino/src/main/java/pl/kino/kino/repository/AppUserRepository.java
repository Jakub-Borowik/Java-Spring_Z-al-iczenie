package pl.kino.kino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLogin(String login);
}
