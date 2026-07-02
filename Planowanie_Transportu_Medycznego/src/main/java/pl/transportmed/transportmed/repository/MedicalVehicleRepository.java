package pl.transportmed.transportmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.transportmed.transportmed.model.MedicalVehicle;

public interface MedicalVehicleRepository extends JpaRepository<MedicalVehicle, Long> {
}
