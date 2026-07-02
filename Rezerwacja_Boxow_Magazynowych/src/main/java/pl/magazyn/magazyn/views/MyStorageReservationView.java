package pl.magazyn.magazyn.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.magazyn.magazyn.model.StorageReservation;
import pl.magazyn.magazyn.service.StorageReservationService;

@Route("storage/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyStorageReservationView extends VerticalLayout {
    public MyStorageReservationView(StorageReservationService service) {
        Grid<StorageReservation> grid = new Grid<>(StorageReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Box");
        grid.addColumn(StorageReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(StorageReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(StorageReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(StorageReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(StorageReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(StorageReservation::getScore).setHeader("Wynik");
        grid.addColumn(StorageReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("storage", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
