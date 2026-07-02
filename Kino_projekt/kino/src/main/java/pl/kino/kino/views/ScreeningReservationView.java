package pl.kino.kino.views;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.kino.kino.dto.HallViewRowResponse;
import pl.kino.kino.dto.HallViewSeatResponse;
import pl.kino.kino.model.Reservation;
import pl.kino.kino.model.Screening;
import pl.kino.kino.model.Seat;
import pl.kino.kino.model.TicketType;
import pl.kino.kino.service.HallViewService;
import pl.kino.kino.service.ReservationService;
import pl.kino.kino.service.SeatAlgorithmService;
import pl.kino.kino.service.ScreeningService;
import pl.kino.kino.service.TicketPriceService;

@Route("screenings/:screeningId")
@PageTitle("Rezerwacja")
@RolesAllowed("CLIENT")
public class ScreeningReservationView extends VerticalLayout implements BeforeEnterObserver {
    private final ScreeningService screeningService;
    private final HallViewService hallViewService;
    private final ReservationService reservationService;
    private final TicketPriceService ticketPriceService;
    private final SeatAlgorithmService seatAlgorithmService;
    private final Div hallView = new Div();
    private final VerticalLayout selectedSeatsLayout = new VerticalLayout();
    private final Span totalPrice = new Span("Suma: 0.00");
    private final IntegerField adjacentSeatsCount = new IntegerField("Miejsca obok siebie");
    private final Map<Long, SelectedSeat> selectedSeats = new LinkedHashMap<>();
    private Long screeningId;

    public ScreeningReservationView(ScreeningService screeningService, HallViewService hallViewService,
            ReservationService reservationService, TicketPriceService ticketPriceService,
            SeatAlgorithmService seatAlgorithmService) {
        this.screeningService = screeningService;
        this.hallViewService = hallViewService;
        this.reservationService = reservationService;
        this.ticketPriceService = ticketPriceService;
        this.seatAlgorithmService = seatAlgorithmService;

        hallView.getStyle().set("display", "grid");
        hallView.getStyle().set("gap", "6px");
        selectedSeatsLayout.setPadding(false);
        selectedSeatsLayout.setSpacing(false);
        adjacentSeatsCount.setValue(3);
        adjacentSeatsCount.setMin(1);
        adjacentSeatsCount.setStepButtonsVisible(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        screeningId = event.getRouteParameters()
                .get("screeningId")
                .map(Long::valueOf)
                .orElseThrow(() -> new RuntimeException("Screening id is required."));
        renderScreening();
    }

    private void renderScreening() {
        removeAll();
        selectedSeats.clear();
        Screening screening = screeningService.getScreeningById(screeningId);
        Button findAdjacentButton = new Button("Znajdz miejsca obok siebie", event -> selectAdjacentSeats());
        Button reserveButton = new Button("Rezerwuj wybrane miejsca", event -> reserveSeats());

        add(new Anchor("screenings", "Wroc do seansow"),
                new H2(screening.getMovieTitle() + " - " + screening.getStartTime()),
                new HorizontalLayout(adjacentSeatsCount, findAdjacentButton),
                hallView,
                new H2("Wybrane miejsca"),
                selectedSeatsLayout,
                totalPrice,
                reserveButton);
        refreshHallView();
        refreshSelectedSeats();
    }

    private void selectAdjacentSeats() {
        try {
            Screening screening = screeningService.getScreeningById(screeningId);
            List<Seat> seats = seatAlgorithmService.findAdjacentSeats(screening, adjacentSeatsCount.getValue());
            selectedSeats.clear();
            IntStream.range(0, seats.size())
                    .mapToObj(seats::get)
                    .forEach(seat -> selectedSeats.put(seat.getId(),
                            new SelectedSeat(seat.getId(), seat.getRowNumber(), seat.getSeatNumber(),
                                    TicketType.NORMAL)));
            refreshHallView();
            refreshSelectedSeats();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void refreshHallView() {
        hallView.removeAll();
        hallViewService.getHallView(screeningId)
                .forEach(row -> hallView.add(rowComponent(row)));
    }

    private HorizontalLayout rowComponent(HallViewRowResponse row) {
        HorizontalLayout rowLayout = new HorizontalLayout();
        rowLayout.setAlignItems(Alignment.CENTER);
        rowLayout.add(new Span("Rzad " + row.getRowNumber()));
        row.getSeats().stream()
                .map(seat -> seatButton(row.getRowNumber(), seat))
                .forEach(rowLayout::add);
        return rowLayout;
    }

    private Button seatButton(int rowNumber, HallViewSeatResponse seat) {
        Button seatButton = new Button(String.valueOf(seat.getSeatNumber()));
        seatButton.setEnabled(!seat.isOccupied());
        seatButton.addClickListener(event -> toggleSeat(rowNumber, seat));
        seatButton.getStyle().set("width", "38px");
        seatButton.getStyle().set("height", "34px");
        seatButton.getStyle().set("padding", "0");
        seatButton.getStyle().set("background", seatBackground(seat));
        return seatButton;
    }

    private String seatBackground(HallViewSeatResponse seat) {
        return java.util.Optional.of(selectedSeats.containsKey(seat.getSeatId()))
                .filter(Boolean::booleanValue)
                .map(selected -> "#8bb8ff")
                .orElse(seat.isOccupied() ? "#f3b6b6" : "#bfe8c8");
    }

    private void toggleSeat(int rowNumber, HallViewSeatResponse seat) {
        java.util.Optional.of(selectedSeats.containsKey(seat.getSeatId()))
                .filter(Boolean::booleanValue)
                .ifPresentOrElse(
                        selected -> selectedSeats.remove(seat.getSeatId()),
                        () -> selectedSeats.put(seat.getSeatId(),
                                new SelectedSeat(seat.getSeatId(), rowNumber, seat.getSeatNumber(), TicketType.NORMAL)));
        refreshHallView();
        refreshSelectedSeats();
    }

    private void refreshSelectedSeats() {
        selectedSeatsLayout.removeAll();
        selectedSeats.values().stream()
                .map(this::selectedSeatRow)
                .forEach(selectedSeatsLayout::add);
        updateTotalPrice();
    }

    private HorizontalLayout selectedSeatRow(SelectedSeat selectedSeat) {
        Select<TicketType> ticketType = new Select<>();
        ticketType.setItems(TicketType.values());
        ticketType.setValue(selectedSeat.ticketType());
        ticketType.addValueChangeListener(event -> {
            selectedSeats.put(selectedSeat.seatId(),
                    new SelectedSeat(selectedSeat.seatId(), selectedSeat.rowNumber(), selectedSeat.seatNumber(),
                            event.getValue()));
            updateTotalPrice();
        });

        HorizontalLayout row = new HorizontalLayout(
                new Span("Rzad " + selectedSeat.rowNumber() + ", miejsce " + selectedSeat.seatNumber()),
                ticketType,
                new Button("Usun", event -> {
                    selectedSeats.remove(selectedSeat.seatId());
                    refreshHallView();
                    refreshSelectedSeats();
                }));
        row.setAlignItems(Alignment.CENTER);
        return row;
    }

    private void updateTotalPrice() {
        BigDecimal price = selectedSeats.values().stream()
                .map(selectedSeat -> ticketPriceService.calculatePrice(selectedSeat.ticketType()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPrice.setText("Suma: " + price);
    }

    private void reserveSeats() {
        try {
            List<Long> seatIds = selectedSeats.values().stream()
                    .map(SelectedSeat::seatId)
                    .toList();
            List<TicketType> ticketTypes = selectedSeats.values().stream()
                    .map(SelectedSeat::ticketType)
                    .toList();
            Reservation reservation = reservationService.createReservationForSelectedSeats(screeningId, seatIds,
                    ticketTypes);
            Notification.show("Rezerwacja ID: " + reservation.getId()
                    + ", koszt: " + reservation.getTotalPrice()
                    + ", miejsca: " + selectedSeatsText(), 5000, Notification.Position.TOP_CENTER);
            selectedSeats.clear();
            refreshHallView();
            refreshSelectedSeats();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private String selectedSeatsText() {
        return selectedSeats.values().stream()
                .map(seat -> "R" + seat.rowNumber() + "M" + seat.seatNumber())
                .collect(Collectors.joining(", "));
    }

    private record SelectedSeat(Long seatId, int rowNumber, int seatNumber, TicketType ticketType) {
    }
}
