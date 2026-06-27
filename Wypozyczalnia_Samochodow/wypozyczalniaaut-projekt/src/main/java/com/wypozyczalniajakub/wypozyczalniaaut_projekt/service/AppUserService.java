package com.wypozyczalniajakub.wypozyczalniaaut_projekt.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.AppUser;
import com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories.AppUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser getLoggedInUser(AppUser user) {
        // Pobranie danych o zalogowanym użytkowniku
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Zabezpieczenie - czy w ogóle jest zalogowany
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("No user is logged in.");
        }

        // Pobranie loginu (domyślnie pod nazwą name)
        String currentLogin = authentication.getName();
        // Szukanie użytkownika w bazie custom metodą z repo
        return appUserRepository.findByLogin(currentLogin)
        .orElseThrow(() -> new RuntimeException("User with the login: " + currentLogin + " can't be found."));
    }
}
