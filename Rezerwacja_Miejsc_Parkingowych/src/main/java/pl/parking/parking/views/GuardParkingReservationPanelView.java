package pl.parking.parking.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.parking.parking.model.ParkingReservation;
import pl.parking.parking.service.ParkingReservationService;

@Route("guard/parking")
@PageTitle("Panel")
@RolesAllowed("GUARD")
public class GuardParkingReservationPanelView extends VerticalLayout {
    private final ParkingReservationService service;
    private final Grid<ParkingReservation> grid = new Grid<>(ParkingReservation.class, false);

    public GuardParkingReservationPanelView(ParkingReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(ParkingReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Miejsce");
        grid.addColumn(ParkingReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(ParkingReservation::getStartTime).setHeader("Start");
        grid.addColumn(ParkingReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(ParkingReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ParkingReservation::getScore).setHeader("Wynik");
        grid.addColumn(ParkingReservation::getTotalPrice).setHeader("Koszt");
        grid.addComponentColumn(record -> new Button("Usun", event -> {
            service.cancelRecord(record.getId());
            refresh();
        }));
        grid.setWidthFull();
    }

    private void refresh() {
        grid.setItems(service.getAllRecords());
    }
}
