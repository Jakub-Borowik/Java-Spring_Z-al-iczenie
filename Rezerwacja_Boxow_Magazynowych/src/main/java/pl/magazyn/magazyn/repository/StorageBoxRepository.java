package pl.magazyn.magazyn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.magazyn.magazyn.model.StorageBox;

public interface StorageBoxRepository extends JpaRepository<StorageBox, Long> {
}
