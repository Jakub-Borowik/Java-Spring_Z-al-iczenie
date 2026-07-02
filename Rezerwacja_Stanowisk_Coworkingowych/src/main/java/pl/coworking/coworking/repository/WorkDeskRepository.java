package pl.coworking.coworking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coworking.coworking.model.WorkDesk;

public interface WorkDeskRepository extends JpaRepository<WorkDesk, Long> {
}
