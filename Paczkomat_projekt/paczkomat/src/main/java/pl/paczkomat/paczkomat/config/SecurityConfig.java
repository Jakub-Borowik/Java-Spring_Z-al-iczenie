package pl.paczkomat.paczkomat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import pl.paczkomat.paczkomat.repository.AppUserRepository;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/courier/**").hasRole("COURIER")
                                                .requestMatchers("/lockers/free/**", "/lockers/occupy/**")
                                                .hasRole("COURIER")
                                                .requestMatchers("/parcels", "/parcels/my")
                                                .hasRole("CLIENT")
                                                .requestMatchers("/login", "/error").permitAll()
                                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .successHandler((request, response, authentication) -> {
                                                        boolean courier = authentication.getAuthorities().stream()
                                                                        .anyMatch(authority -> "ROLE_COURIER"
                                                                                        .equals(authority
                                                                                                        .getAuthority()));
                                                        response.sendRedirect(courier ? "/courier/lockers" : "/");
                                                })
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                .build();
        }

        @Bean
        UserDetailsService userDetailsService(AppUserRepository appUserRepository) {
                return username -> appUserRepository.findByLogin(username)
                                .map(user -> org.springframework.security.core.userdetails.User
                                                .withUsername(user.getLogin())
                                                .password(user.getPassword())
                                                .roles(user.getRole().name())
                                                .build())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
