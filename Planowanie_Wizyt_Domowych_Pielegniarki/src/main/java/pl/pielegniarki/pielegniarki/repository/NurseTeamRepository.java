package pl.pielegniarki.pielegniarki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pielegniarki.pielegniarki.model.NurseTeam;

public interface NurseTeamRepository extends JpaRepository<NurseTeam, Long> {
}
