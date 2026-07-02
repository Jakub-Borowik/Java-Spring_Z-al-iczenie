package pl.coworking.coworking.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.coworking.coworking.model.CoworkingBooking;
import pl.coworking.coworking.service.CoworkingBookingService;

@Route("manager/coworking-bookings")
@PageTitle("Panel")
@RolesAllowed("MANAGER")
public class ManagerCoworkingBookingPanelView extends VerticalLayout {
    private final CoworkingBookingService service;
    private final Grid<CoworkingBooking> grid = new Grid<>(CoworkingBooking.class, false);

    public ManagerCoworkingBookingPanelView(CoworkingBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(CoworkingBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Stanowisko");
        grid.addColumn(CoworkingBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(CoworkingBooking::getStartTime).setHeader("Start");
        grid.addColumn(CoworkingBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(CoworkingBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CoworkingBooking::getScore).setHeader("Wynik");
        grid.addColumn(CoworkingBooking::getTotalPrice).setHeader("Koszt");
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
