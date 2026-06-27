package com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // SELECT * FROM app_user WHERE login = ?
    Optional<AppUser> findByLogin(String login);
}
