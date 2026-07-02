package pl.ladowarki.ladowarki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ladowarki.ladowarki.model.ChargingStation;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {
}
