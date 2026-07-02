package pl.diety.diety.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.diety.diety.model.MealDelivery;
import pl.diety.diety.service.MealDeliveryService;

@Route("dispatcher/meal-deliveries")
@PageTitle("Panel")
@RolesAllowed("DISPATCHER")
public class DispatcherMealDeliveryPanelView extends VerticalLayout {
    private final MealDeliveryService service;
    private final Grid<MealDelivery> grid = new Grid<>(MealDelivery.class, false);

    public DispatcherMealDeliveryPanelView(MealDeliveryService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(MealDelivery::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Trasa");
        grid.addColumn(MealDelivery::getCategory).setHeader("Kategoria");
        grid.addColumn(MealDelivery::getStartTime).setHeader("Start");
        grid.addColumn(MealDelivery::getEndTime).setHeader("Koniec");
        grid.addColumn(MealDelivery::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MealDelivery::getScore).setHeader("Wynik");
        grid.addColumn(MealDelivery::getTotalPrice).setHeader("Koszt");
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
