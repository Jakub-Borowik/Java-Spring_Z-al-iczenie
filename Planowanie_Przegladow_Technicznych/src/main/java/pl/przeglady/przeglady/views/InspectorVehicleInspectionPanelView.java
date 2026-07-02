package pl.przeglady.przeglady.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.przeglady.przeglady.model.VehicleInspection;
import pl.przeglady.przeglady.service.VehicleInspectionService;

@Route("inspector/inspections")
@PageTitle("Panel")
@RolesAllowed("INSPECTOR")
public class InspectorVehicleInspectionPanelView extends VerticalLayout {
    private final VehicleInspectionService service;
    private final Grid<VehicleInspection> grid = new Grid<>(VehicleInspection.class, false);

    public InspectorVehicleInspectionPanelView(VehicleInspectionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(VehicleInspection::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Stanowisko");
        grid.addColumn(VehicleInspection::getCategory).setHeader("Kategoria");
        grid.addColumn(VehicleInspection::getStartTime).setHeader("Start");
        grid.addColumn(VehicleInspection::getEndTime).setHeader("Koniec");
        grid.addColumn(VehicleInspection::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(VehicleInspection::getScore).setHeader("Wynik");
        grid.addColumn(VehicleInspection::getTotalPrice).setHeader("Koszt");
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
