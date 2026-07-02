package pl.fotowoltaika.fotowoltaika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fotowoltaika.fotowoltaika.model.SolarTechnician;

public interface SolarTechnicianRepository extends JpaRepository<SolarTechnician, Long> {
}
