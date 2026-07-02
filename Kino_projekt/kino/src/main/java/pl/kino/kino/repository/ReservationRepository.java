package pl.kino.kino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.AppUser;
import pl.kino.kino.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(AppUser user);
}
