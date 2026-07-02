package pl.odpady.odpady.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.odpady.odpady.model.GarbageTruck;

public interface GarbageTruckRepository extends JpaRepository<GarbageTruck, Long> {
}
