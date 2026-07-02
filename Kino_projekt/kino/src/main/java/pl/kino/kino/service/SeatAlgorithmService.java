package pl.kino.kino.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.kino.kino.model.PaymentStatus;
import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Seat;
import pl.kino.kino.repository.SeatRepository;
import pl.kino.kino.repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class SeatAlgorithmService {
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public List<Seat> findAdjacentSeats(Screening screening, int seatsCount) {
        int requestedSeats = Optional.of(seatsCount)
                .filter(count -> count > 0)
                .orElseThrow(() -> new RuntimeException("Seats count must be greater than zero."));

        Set<Long> occupiedSeatIds = ticketRepository.findByReservation_Screening(screening).stream()
                .filter(ticket -> ticket.getReservation().getPaymentStatus() != PaymentStatus.CANCELLED)
                .map(ticket -> ticket.getSeat().getId())
                .collect(Collectors.toSet());

        return seatRepository.findByHall(screening.getHall()).stream()
                .filter(seat -> !occupiedSeatIds.contains(seat.getId()))
                .collect(Collectors.groupingBy(Seat::getRowNumber))
                .values()
                .stream()
                .map(rowSeats -> rowSeats.stream()
                        .sorted(Comparator.comparing(Seat::getSeatNumber))
                        .toList())
                .sorted(Comparator.comparing(rowSeats -> rowSeats.getFirst().getRowNumber()))
                .map(rowSeats -> findAdjacentSeatsInRow(rowSeats, requestedSeats))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No adjacent seats available."));
    }

    private Optional<List<Seat>> findAdjacentSeatsInRow(List<Seat> rowSeats, int seatsCount) {
        return Optional.of(rowSeats)
                .filter(seats -> seats.size() >= seatsCount)
                .flatMap(seats -> IntStream.rangeClosed(0, seats.size() - seatsCount)
                        .mapToObj(startIndex -> seats.subList(startIndex, startIndex + seatsCount))
                        .filter(this::areSeatsNextToEachOther)
                        .findFirst());
    }

    private boolean areSeatsNextToEachOther(List<Seat> seats) {
        return IntStream.range(1, seats.size())
                .allMatch(index -> seats.get(index).getSeatNumber() == seats.getFirst().getSeatNumber() + index);
    }
}
