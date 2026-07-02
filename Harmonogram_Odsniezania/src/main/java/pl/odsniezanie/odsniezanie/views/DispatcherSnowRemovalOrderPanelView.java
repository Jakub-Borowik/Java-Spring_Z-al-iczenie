package pl.odsniezanie.odsniezanie.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.odsniezanie.odsniezanie.model.SnowRemovalOrder;
import pl.odsniezanie.odsniezanie.service.SnowRemovalOrderService;

@Route("dispatcher/snow-removal")
@PageTitle("Panel")
@RolesAllowed("DISPATCHER")
public class DispatcherSnowRemovalOrderPanelView extends VerticalLayout {
    private final SnowRemovalOrderService service;
    private final Grid<SnowRemovalOrder> grid = new Grid<>(SnowRemovalOrder.class, false);

    public DispatcherSnowRemovalOrderPanelView(SnowRemovalOrderService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(SnowRemovalOrder::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(SnowRemovalOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(SnowRemovalOrder::getStartTime).setHeader("Start");
        grid.addColumn(SnowRemovalOrder::getEndTime).setHeader("Koniec");
        grid.addColumn(SnowRemovalOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SnowRemovalOrder::getScore).setHeader("Wynik");
        grid.addColumn(SnowRemovalOrder::getTotalPrice).setHeader("Koszt");
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
