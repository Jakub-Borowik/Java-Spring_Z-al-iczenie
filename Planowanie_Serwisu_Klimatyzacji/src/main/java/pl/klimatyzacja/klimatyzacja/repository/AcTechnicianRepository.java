package pl.klimatyzacja.klimatyzacja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klimatyzacja.klimatyzacja.model.AcTechnician;

public interface AcTechnicianRepository extends JpaRepository<AcTechnician, Long> {
}
