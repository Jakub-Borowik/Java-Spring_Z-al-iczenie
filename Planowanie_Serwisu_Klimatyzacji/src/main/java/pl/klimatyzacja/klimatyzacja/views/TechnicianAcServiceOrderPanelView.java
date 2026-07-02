package pl.klimatyzacja.klimatyzacja.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.klimatyzacja.klimatyzacja.model.AcServiceOrder;
import pl.klimatyzacja.klimatyzacja.service.AcServiceOrderService;

@Route("technician/ac-services")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianAcServiceOrderPanelView extends VerticalLayout {
    private final AcServiceOrderService service;
    private final Grid<AcServiceOrder> grid = new Grid<>(AcServiceOrder.class, false);

    public TechnicianAcServiceOrderPanelView(AcServiceOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(AcServiceOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Technik");
        grid.addColumn(AcServiceOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(AcServiceOrder::getStartTime).setHeader("Start");
        grid.addColumn(AcServiceOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(AcServiceOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(AcServiceOrder::getScore).setHeader("Wynik");
        grid.addColumn(AcServiceOrder::getTotalPrice).setHeader("Koszt");
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
