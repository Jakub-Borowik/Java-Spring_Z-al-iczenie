package pl.odsniezanie.odsniezanie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.odsniezanie.odsniezanie.model.SnowRemovalVehicle;

public interface SnowRemovalVehicleRepository extends JpaRepository<SnowRemovalVehicle, Long> {
}
