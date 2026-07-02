package pl.naukajazdy.naukajazdy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.naukajazdy.naukajazdy.model.DrivingInstructor;

public interface DrivingInstructorRepository extends JpaRepository<DrivingInstructor, Long> {
}
