package pl.remonty.remonty.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.remonty.remonty.model.RenovationOrder;
import pl.remonty.remonty.service.RenovationOrderService;

@Route("coordinator/renovations")
@PageTitle("Panel")
@RolesAllowed("COORDINATOR")
public class CoordinatorRenovationOrderPanelView extends VerticalLayout {
    private final RenovationOrderService service;
    private final Grid<RenovationOrder> grid = new Grid<>(RenovationOrder.class, false);

    public CoordinatorRenovationOrderPanelView(RenovationOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(RenovationOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ekipa");
        grid.addColumn(RenovationOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(RenovationOrder::getStartTime).setHeader("Start");
        grid.addColumn(RenovationOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(RenovationOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(RenovationOrder::getScore).setHeader("Wynik");
        grid.addColumn(RenovationOrder::getTotalPrice).setHeader("Koszt");
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
