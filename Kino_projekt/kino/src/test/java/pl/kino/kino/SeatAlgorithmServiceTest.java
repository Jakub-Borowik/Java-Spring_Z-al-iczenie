package pl.kino.kino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.kino.kino.model.Hall;
import pl.kino.kino.model.PaymentStatus;
import pl.kino.kino.model.Reservation;
import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Seat;
import pl.kino.kino.model.Ticket;
import pl.kino.kino.repository.SeatRepository;
import pl.kino.kino.repository.TicketRepository;
import pl.kino.kino.service.SeatAlgorithmService;

@ExtendWith(MockitoExtension.class)
class SeatAlgorithmServiceTest {
    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private SeatAlgorithmService seatAlgorithmService;

    @Test
    void shouldFindThreeAdjacentSeatsInFirstRow() {
        Hall hall = hall();
        Screening screening = screening(hall);
        when(seatRepository.findByHall(hall)).thenReturn(List.of(
                seat(1L, 1, 1, hall),
                seat(2L, 1, 2, hall),
                seat(3L, 1, 3, hall)));
        when(ticketRepository.findByReservation_Screening(screening)).thenReturn(List.of());

        List<Seat> result = seatAlgorithmService.findAdjacentSeats(screening, 3);

        assertSeatNumbers(result, List.of(1, 2, 3));
    }

    @Test
    void shouldSkipOccupiedSeatBetweenFreeSeats() {
        Hall hall = hall();
        Screening screening = screening(hall);
        Seat occupiedSeat = seat(2L, 1, 2, hall);
        when(seatRepository.findByHall(hall)).thenReturn(List.of(
                seat(1L, 1, 1, hall),
                occupiedSeat,
                seat(3L, 1, 3, hall),
                seat(4L, 1, 4, hall),
                seat(5L, 1, 5, hall)));
        when(ticketRepository.findByReservation_Screening(screening)).thenReturn(List.of(ticket(screening, occupiedSeat)));

        List<Seat> result = seatAlgorithmService.findAdjacentSeats(screening, 3);

        assertSeatNumbers(result, List.of(3, 4, 5));
    }

    @Test
    void shouldFindSeatsInSecondRowWhenFirstRowHasNoEnoughAdjacentSeats() {
        Hall hall = hall();
        Screening screening = screening(hall);
        Seat occupiedSeat = seat(2L, 1, 2, hall);
        when(seatRepository.findByHall(hall)).thenReturn(List.of(
                seat(1L, 1, 1, hall),
                occupiedSeat,
                seat(3L, 1, 3, hall),
                seat(4L, 2, 1, hall),
                seat(5L, 2, 2, hall),
                seat(6L, 2, 3, hall)));
        when(ticketRepository.findByReservation_Screening(screening)).thenReturn(List.of(ticket(screening, occupiedSeat)));

        List<Seat> result = seatAlgorithmService.findAdjacentSeats(screening, 3);

        assertEquals(2, result.getFirst().getRowNumber());
        assertSeatNumbers(result, List.of(1, 2, 3));
    }

    @Test
    void shouldThrowExceptionWhenNoAdjacentSeatsAvailable() {
        Hall hall = hall();
        Screening screening = screening(hall);
        Seat occupiedSeat = seat(2L, 1, 2, hall);
        when(seatRepository.findByHall(hall)).thenReturn(List.of(
                seat(1L, 1, 1, hall),
                occupiedSeat,
                seat(3L, 1, 3, hall)));
        when(ticketRepository.findByReservation_Screening(screening)).thenReturn(List.of(ticket(screening, occupiedSeat)));

        assertThrows(RuntimeException.class, () -> seatAlgorithmService.findAdjacentSeats(screening, 2));
    }

    private void assertSeatNumbers(List<Seat> seats, List<Integer> expectedNumbers) {
        assertEquals(expectedNumbers, seats.stream()
                .map(Seat::getSeatNumber)
                .toList());
    }

    private Hall hall() {
        Hall hall = new Hall();
        hall.setId(1L);
        hall.setName("Sala 1");
        return hall;
    }

    private Screening screening(Hall hall) {
        Screening screening = new Screening();
        screening.setId(1L);
        screening.setHall(hall);
        return screening;
    }

    private Seat seat(Long id, int rowNumber, int seatNumber, Hall hall) {
        Seat seat = new Seat();
        seat.setId(id);
        seat.setRowNumber(rowNumber);
        seat.setSeatNumber(seatNumber);
        seat.setHall(hall);
        return seat;
    }

    private Ticket ticket(Screening screening, Seat seat) {
        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setPaymentStatus(PaymentStatus.WAITING_FOR_PAYMENT);

        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setSeat(seat);
        return ticket;
    }
}
