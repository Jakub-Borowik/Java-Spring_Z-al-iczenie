package pl.okna.okna.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.okna.okna.model.WindowCleaningOrder;
import pl.okna.okna.service.WindowCleaningOrderService;

@Route("supervisor/window-cleaning")
@PageTitle("Panel")
@RolesAllowed("SUPERVISOR")
public class SupervisorWindowCleaningOrderPanelView extends VerticalLayout {
    private final WindowCleaningOrderService service;
    private final Grid<WindowCleaningOrder> grid = new Grid<>(WindowCleaningOrder.class, false);

    public SupervisorWindowCleaningOrderPanelView(WindowCleaningOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(WindowCleaningOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ekipa");
        grid.addColumn(WindowCleaningOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(WindowCleaningOrder::getStartTime).setHeader("Start");
        grid.addColumn(WindowCleaningOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(WindowCleaningOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WindowCleaningOrder::getScore).setHeader("Wynik");
        grid.addColumn(WindowCleaningOrder::getTotalPrice).setHeader("Koszt");
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
