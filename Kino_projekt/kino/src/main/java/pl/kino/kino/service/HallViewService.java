package pl.kino.kino.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.kino.kino.dto.HallViewRowResponse;
import pl.kino.kino.dto.HallViewSeatResponse;
import pl.kino.kino.model.PaymentStatus;
import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Seat;
import pl.kino.kino.repository.SeatRepository;
import pl.kino.kino.repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class HallViewService {
    private final ScreeningService screeningService;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public List<HallViewRowResponse> getHallView(Long screeningId) {
        Screening screening = screeningService.getScreeningById(screeningId);

        Set<Long> occupiedSeatIds = ticketRepository.findByReservation_Screening(screening).stream()
                .filter(ticket -> ticket.getReservation().getPaymentStatus() != PaymentStatus.CANCELLED)
                .map(ticket -> ticket.getSeat().getId())
                .collect(Collectors.toSet());

        return seatRepository.findByHall(screening.getHall()).stream()
                .sorted(Comparator.comparing(Seat::getSeatNumber))
                .collect(Collectors.groupingBy(Seat::getRowNumber))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(row -> row.getKey()))
                .map(row -> new HallViewRowResponse(
                        row.getKey(),
                        row.getValue().stream()
                                .sorted(Comparator.comparing(Seat::getSeatNumber))
                                .map(seat -> new HallViewSeatResponse(
                                        seat.getId(),
                                        seat.getSeatNumber(),
                                        occupiedSeatIds.contains(seat.getId())))
                                .toList()))
                .toList();
    }
}
