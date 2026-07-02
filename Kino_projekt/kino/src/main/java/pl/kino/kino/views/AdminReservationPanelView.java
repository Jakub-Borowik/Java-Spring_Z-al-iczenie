package pl.kino.kino.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.kino.kino.model.Reservation;
import pl.kino.kino.service.ReservationService;

@Route("admin/reservation-panel")
@PageTitle("Admin - rezerwacje")
@RolesAllowed("ADMIN")
public class AdminReservationPanelView extends VerticalLayout {
    private final ReservationService reservationService;
    private final Grid<Reservation> reservationGrid = new Grid<>(Reservation.class, false);

    public AdminReservationPanelView(ReservationService reservationService) {
        this.reservationService = reservationService;

        configureGrid();
        add(new H2("Admin - wszystkie rezerwacje"), reservationGrid);
        refreshReservations();
    }

    private void configureGrid() {
        reservationGrid.addColumn(Reservation::getId).setHeader("ID");
        reservationGrid.addColumn(reservation -> reservation.getUser().getLogin()).setHeader("Klient");
        reservationGrid.addColumn(reservation -> reservation.getScreening().getMovieTitle()).setHeader("Film");
        reservationGrid.addColumn(Reservation::getPaymentStatus).setHeader("Platnosc");
        reservationGrid.addColumn(Reservation::getTotalPrice).setHeader("Cena");
        reservationGrid.addComponentColumn(reservation -> new Button("Usun",
                event -> deleteReservation(reservation.getId())));
        reservationGrid.setWidthFull();
    }

    private void deleteReservation(Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            Notification.show("Rezerwacja usunieta.", 3000, Notification.Position.TOP_CENTER);
            refreshReservations();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void refreshReservations() {
        reservationGrid.setItems(reservationService.getAllReservations());
    }
}
