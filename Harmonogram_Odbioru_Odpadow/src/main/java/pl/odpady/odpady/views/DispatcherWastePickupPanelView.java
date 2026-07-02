package pl.odpady.odpady.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.odpady.odpady.model.WastePickup;
import pl.odpady.odpady.service.WastePickupService;

@Route("dispatcher/waste-pickups")
@PageTitle("Panel")
@RolesAllowed("DISPATCHER")
public class DispatcherWastePickupPanelView extends VerticalLayout {
    private final WastePickupService service;
    private final Grid<WastePickup> grid = new Grid<>(WastePickup.class, false);

    public DispatcherWastePickupPanelView(WastePickupService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(WastePickup::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(WastePickup::getCategory).setHeader("Kategoria");
        grid.addColumn(WastePickup::getStartTime).setHeader("Start");
        grid.addColumn(WastePickup::getEndTime).setHeader("Koniec");
        grid.addColumn(WastePickup::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WastePickup::getScore).setHeader("Wynik");
        grid.addColumn(WastePickup::getTotalPrice).setHeader("Koszt");
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
