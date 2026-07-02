package pl.kino.kino.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.kino.kino.model.AppUser;
import pl.kino.kino.repository.AppUserRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @SuppressWarnings("null")
    public AppUser getLoggedInUser() {
        // Pobranie danych o zalogowanym użytkowniku
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return java.util.Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .filter(auth -> !"anonymousUser".equals(auth.getPrincipal()))
                .map(Authentication::getName)
                
                .flatMap(appUserRepository::findByLogin)
                .orElseThrow(() -> new RuntimeException("No user is logged in."));
    }
}
