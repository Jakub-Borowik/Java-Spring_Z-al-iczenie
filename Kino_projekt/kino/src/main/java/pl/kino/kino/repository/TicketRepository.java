package pl.kino.kino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByReservation_Screening(Screening screening);
}
