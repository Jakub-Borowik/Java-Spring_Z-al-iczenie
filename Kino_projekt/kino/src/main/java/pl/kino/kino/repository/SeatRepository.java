package pl.kino.kino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.Hall;
import pl.kino.kino.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByHall(Hall hall);

    List<Seat> findByIdIn(List<Long> ids);
}
