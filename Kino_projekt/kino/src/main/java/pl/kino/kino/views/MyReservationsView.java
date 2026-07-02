package pl.kino.kino.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.kino.kino.model.Reservation;
import pl.kino.kino.service.ReservationService;

@Route("screenings/my-reservations")
@PageTitle("Moje rezerwacje")
@RolesAllowed("CLIENT")
public class MyReservationsView extends VerticalLayout {
    private final ReservationService reservationService;
    private final Grid<Reservation> reservationGrid = new Grid<>(Reservation.class, false);

    public MyReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;

        configureGrid();
        add(new Anchor("screenings", "Seanse"), new H2("Moje rezerwacje"), reservationGrid);
        refreshReservations();
    }

    private void configureGrid() {
        reservationGrid.addColumn(Reservation::getId).setHeader("ID");
        reservationGrid.addColumn(reservation -> reservation.getScreening().getMovieTitle()).setHeader("Film");
        reservationGrid.addColumn(Reservation::getPaymentStatus).setHeader("Platnosc");
        reservationGrid.addColumn(Reservation::getTotalPrice).setHeader("Cena");
        reservationGrid.addComponentColumn(reservation -> new Button("Zaplac",
                event -> payForReservation(reservation.getId())));
        reservationGrid.addComponentColumn(reservation -> new Button("Anuluj platnosc",
                event -> cancelPayment(reservation.getId())));
        reservationGrid.setWidthFull();
    }

    private void payForReservation(Long reservationId) {
        try {
            reservationService.payForReservation(reservationId);
            Notification.show("Platnosc wykonana.", 3000, Notification.Position.TOP_CENTER);
            refreshReservations();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void cancelPayment(Long reservationId) {
        try {
            reservationService.cancelPayment(reservationId);
            Notification.show("Platnosc anulowana.", 3000, Notification.Position.TOP_CENTER);
            refreshReservations();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void refreshReservations() {
        reservationGrid.setItems(reservationService.getLoggedInUserReservations());
    }
}
