package pl.odpady.odpady.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.odpady.odpady.model.WastePickup;
import pl.odpady.odpady.service.WastePickupService;

@Route("waste-pickups/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyWastePickupView extends VerticalLayout {
    public MyWastePickupView(WastePickupService service) {
        Grid<WastePickup> grid = new Grid<>(WastePickup.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(WastePickup::getCategory).setHeader("Kategoria");
        grid.addColumn(WastePickup::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(WastePickup::getMinQuality).setHeader("Jakosc");
        grid.addColumn(WastePickup::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(WastePickup::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WastePickup::getScore).setHeader("Wynik");
        grid.addColumn(WastePickup::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("waste-pickups", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
