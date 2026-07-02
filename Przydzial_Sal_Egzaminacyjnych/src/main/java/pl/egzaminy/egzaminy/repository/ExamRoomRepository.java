package pl.egzaminy.egzaminy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.egzaminy.egzaminy.model.ExamRoom;

public interface ExamRoomRepository extends JpaRepository<ExamRoom, Long> {
}
