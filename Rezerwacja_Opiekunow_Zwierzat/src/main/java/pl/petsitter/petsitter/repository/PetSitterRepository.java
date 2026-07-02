package pl.petsitter.petsitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.petsitter.petsitter.model.PetSitter;

public interface PetSitterRepository extends JpaRepository<PetSitter, Long> {
}
