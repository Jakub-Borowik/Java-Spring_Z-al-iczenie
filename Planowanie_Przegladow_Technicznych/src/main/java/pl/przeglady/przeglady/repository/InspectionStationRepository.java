package pl.przeglady.przeglady.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przeglady.przeglady.model.InspectionStation;

public interface InspectionStationRepository extends JpaRepository<InspectionStation, Long> {
}
