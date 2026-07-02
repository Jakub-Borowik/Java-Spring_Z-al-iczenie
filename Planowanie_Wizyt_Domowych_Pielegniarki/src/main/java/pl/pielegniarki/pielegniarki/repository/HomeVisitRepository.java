package pl.pielegniarki.pielegniarki.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pielegniarki.pielegniarki.model.HomeVisit;

public interface HomeVisitRepository extends JpaRepository<HomeVisit, Long> {
    List<HomeVisit> findByUserId(Long userId);

    @Query("""
            select count(record) > 0 from HomeVisit record
            where record.resource.id = :resourceId
            and record.startTime < :endTime
            and record.endTime > :startTime
            """)
    boolean existsOverlapping(@Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
