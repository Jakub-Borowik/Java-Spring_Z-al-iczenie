package pl.kino.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {
    
}
