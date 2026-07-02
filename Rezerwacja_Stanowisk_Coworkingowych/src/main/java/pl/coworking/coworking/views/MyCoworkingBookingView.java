package pl.coworking.coworking.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.coworking.coworking.model.CoworkingBooking;
import pl.coworking.coworking.service.CoworkingBookingService;

@Route("coworking-bookings/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyCoworkingBookingView extends VerticalLayout {
    public MyCoworkingBookingView(CoworkingBookingService service) {
        Grid<CoworkingBooking> grid = new Grid<>(CoworkingBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Stanowisko");
        grid.addColumn(CoworkingBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(CoworkingBooking::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(CoworkingBooking::getMinQuality).setHeader("Jakosc");
        grid.addColumn(CoworkingBooking::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(CoworkingBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(CoworkingBooking::getScore).setHeader("Wynik");
        grid.addColumn(CoworkingBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("coworking-bookings", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
