package pl.pracownie.pracownie.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pracownie.pracownie.model.LabReservation;
import pl.pracownie.pracownie.service.LabReservationService;

@Route("school-labs/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyLabReservationView extends VerticalLayout {
    public MyLabReservationView(LabReservationService service) {
        Grid<LabReservation> grid = new Grid<>(LabReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pracownia");
        grid.addColumn(LabReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(LabReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(LabReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(LabReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(LabReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LabReservation::getScore).setHeader("Wynik");
        grid.addColumn(LabReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("school-labs", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
