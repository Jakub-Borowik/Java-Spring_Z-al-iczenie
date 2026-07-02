package pl.terapia.terapia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.terapia.terapia.model.Therapist;

public interface TherapistRepository extends JpaRepository<Therapist, Long> {
}
