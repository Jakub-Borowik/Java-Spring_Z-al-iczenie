package pl.magazyn.magazyn.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.magazyn.magazyn.model.StorageReservation;
import pl.magazyn.magazyn.service.StorageReservationService;

@Route("warehouse/storage")
@PageTitle("Panel")
@RolesAllowed("WAREHOUSE")
public class WarehouseStorageReservationPanelView extends VerticalLayout {
    private final StorageReservationService service;
    private final Grid<StorageReservation> grid = new Grid<>(StorageReservation.class, false);

    public WarehouseStorageReservationPanelView(StorageReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(StorageReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Box");
        grid.addColumn(StorageReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(StorageReservation::getStartTime).setHeader("Start");
        grid.addColumn(StorageReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(StorageReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(StorageReservation::getScore).setHeader("Wynik");
        grid.addColumn(StorageReservation::getTotalPrice).setHeader("Koszt");
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
