package pl.przeglady.przeglady.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.przeglady.przeglady.model.VehicleInspection;
import pl.przeglady.przeglady.service.VehicleInspectionService;

@Route("inspections/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyVehicleInspectionView extends VerticalLayout {
    public MyVehicleInspectionView(VehicleInspectionService service) {
        Grid<VehicleInspection> grid = new Grid<>(VehicleInspection.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Stanowisko");
        grid.addColumn(VehicleInspection::getCategory).setHeader("Kategoria");
        grid.addColumn(VehicleInspection::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(VehicleInspection::getMinQuality).setHeader("Jakosc");
        grid.addColumn(VehicleInspection::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(VehicleInspection::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(VehicleInspection::getScore).setHeader("Wynik");
        grid.addColumn(VehicleInspection::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("inspections", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
