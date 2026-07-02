package pl.parking.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.parking.parking.model.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}
