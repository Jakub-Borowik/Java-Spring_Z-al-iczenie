package pl.paczkomat.paczkomat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.paczkomat.paczkomat.model.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findByOccupied(boolean occupied);
}
