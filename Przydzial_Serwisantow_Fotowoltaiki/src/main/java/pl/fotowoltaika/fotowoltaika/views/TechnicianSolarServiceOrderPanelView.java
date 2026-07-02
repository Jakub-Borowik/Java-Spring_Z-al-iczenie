package pl.fotowoltaika.fotowoltaika.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fotowoltaika.fotowoltaika.model.SolarServiceOrder;
import pl.fotowoltaika.fotowoltaika.service.SolarServiceOrderService;

@Route("technician/solar-services")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianSolarServiceOrderPanelView extends VerticalLayout {
    private final SolarServiceOrderService service;
    private final Grid<SolarServiceOrder> grid = new Grid<>(SolarServiceOrder.class, false);

    public TechnicianSolarServiceOrderPanelView(SolarServiceOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(SolarServiceOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Serwisant");
        grid.addColumn(SolarServiceOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(SolarServiceOrder::getStartTime).setHeader("Start");
        grid.addColumn(SolarServiceOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(SolarServiceOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SolarServiceOrder::getScore).setHeader("Wynik");
        grid.addColumn(SolarServiceOrder::getTotalPrice).setHeader("Koszt");
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
