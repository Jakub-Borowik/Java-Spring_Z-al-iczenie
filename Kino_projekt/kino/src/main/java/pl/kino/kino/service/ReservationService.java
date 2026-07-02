package pl.kino.kino.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.kino.kino.model.AppUser;
import pl.kino.kino.model.PaymentStatus;
import pl.kino.kino.model.Reservation;
import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Seat;
import pl.kino.kino.model.Ticket;
import pl.kino.kino.model.TicketType;
import pl.kino.kino.repository.ReservationRepository;
import pl.kino.kino.repository.SeatRepository;
import pl.kino.kino.repository.TicketRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AppUserService appUserService;
    private final ScreeningService screeningService;
    private final SeatAlgorithmService seatAlgorithmService;
    private final TicketPriceService ticketPriceService;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public Reservation createReservation(Long screeningId, List<TicketType> ticketTypes) {
        AppUser user = appUserService.getLoggedInUser();
        Screening screening = screeningService.getScreeningById(screeningId);
        List<TicketType> requestedTicketTypes = Optional.ofNullable(ticketTypes)
                .filter(types -> !types.isEmpty())
                .orElseThrow(() -> new RuntimeException("At least one ticket type is required."));
        List<Seat> seats = seatAlgorithmService.findAdjacentSeats(screening, requestedTicketTypes.size());

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setScreening(screening);
        reservation.setPaymentStatus(PaymentStatus.WAITING_FOR_PAYMENT);

        List<Ticket> tickets = IntStream.range(0, requestedTicketTypes.size())
                .mapToObj(index -> createTicket(reservation, seats.get(index), requestedTicketTypes.get(index)))
                .toList();

        reservation.setTickets(tickets);
        reservation.setTotalPrice(tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return reservationRepository.save(reservation);
    }

    public Reservation createReservationForSelectedSeats(Long screeningId, List<Long> seatIds, List<TicketType> ticketTypes) {
        AppUser user = appUserService.getLoggedInUser();
        Screening screening = screeningService.getScreeningById(screeningId);
        List<Long> selectedSeatIds = Optional.ofNullable(seatIds)
                .filter(ids -> !ids.isEmpty())
                .orElseThrow(() -> new RuntimeException("Select at least one seat."));
        List<TicketType> selectedTicketTypes = Optional.ofNullable(ticketTypes)
                .filter(types -> types.size() == selectedSeatIds.size())
                .orElseThrow(() -> new RuntimeException("Every selected seat must have ticket type."));
        List<Seat> seats = seatRepository.findByIdIn(selectedSeatIds);

        Optional.of(seats)
                .filter(foundSeats -> foundSeats.size() == selectedSeatIds.size())
                .orElseThrow(() -> new RuntimeException("Some seats were not found."));

        Optional.of(seats)
                .filter(foundSeats -> foundSeats.stream()
                        .allMatch(seat -> seat.getHall().getId().equals(screening.getHall().getId())))
                .orElseThrow(() -> new RuntimeException("Selected seats do not belong to screening hall."));

        List<Long> occupiedSeatIds = ticketRepository.findByReservation_Screening(screening).stream()
                .filter(ticket -> ticket.getReservation().getPaymentStatus() != PaymentStatus.CANCELLED)
                .map(ticket -> ticket.getSeat().getId())
                .toList();

        Optional.of(seats)
                .filter(foundSeats -> foundSeats.stream()
                        .noneMatch(seat -> occupiedSeatIds.contains(seat.getId())))
                .orElseThrow(() -> new RuntimeException("One of selected seats is already occupied."));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setScreening(screening);
        reservation.setPaymentStatus(PaymentStatus.WAITING_FOR_PAYMENT);

        List<Ticket> tickets = IntStream.range(0, selectedSeatIds.size())
                .mapToObj(index -> createTicket(
                        reservation,
                        findSeatById(seats, selectedSeatIds.get(index)),
                        selectedTicketTypes.get(index)))
                .toList();

        reservation.setTickets(tickets);
        reservation.setTotalPrice(tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getLoggedInUserReservations() {
        AppUser user = appUserService.getLoggedInUser();
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation cancelPayment(Long reservationId) {
        AppUser user = appUserService.getLoggedInUser();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        Optional.of(reservation)
                .filter(foundReservation -> foundReservation.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("You can cancel only your own payment."));

        reservation.setPaymentStatus(PaymentStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    public Reservation payForReservation(Long reservationId) {
        AppUser user = appUserService.getLoggedInUser();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        Optional.of(reservation)
                .filter(foundReservation -> foundReservation.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("You can pay only your own reservation."));

        Optional.of(reservation)
                .filter(foundReservation -> foundReservation.getPaymentStatus() == PaymentStatus.WAITING_FOR_PAYMENT)
                .orElseThrow(() -> new RuntimeException("Only waiting payment can be paid."));

        reservation.setPaymentStatus(PaymentStatus.PAID);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));
        reservationRepository.delete(reservation);
    }

    private Ticket createTicket(Reservation reservation, Seat seat, TicketType ticketType) {
        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setSeat(seat);
        ticket.setTicketType(ticketType);
        ticket.setPrice(ticketPriceService.calculatePrice(ticketType));
        return ticket;
    }

    private Seat findSeatById(List<Seat> seats, Long seatId) {
        return seats.stream()
                .filter(seat -> seat.getId().equals(seatId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Seat not found."));
    }
}
