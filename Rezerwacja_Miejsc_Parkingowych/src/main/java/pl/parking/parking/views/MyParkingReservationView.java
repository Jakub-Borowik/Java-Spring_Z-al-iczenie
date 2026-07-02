package pl.parking.parking.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.parking.parking.model.ParkingReservation;
import pl.parking.parking.service.ParkingReservationService;

@Route("parking/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyParkingReservationView extends VerticalLayout {
    public MyParkingReservationView(ParkingReservationService service) {
        Grid<ParkingReservation> grid = new Grid<>(ParkingReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Miejsce");
        grid.addColumn(ParkingReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(ParkingReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(ParkingReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(ParkingReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(ParkingReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ParkingReservation::getScore).setHeader("Wynik");
        grid.addColumn(ParkingReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("parking", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
