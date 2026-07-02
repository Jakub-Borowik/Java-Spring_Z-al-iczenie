package pl.kino.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    
}
