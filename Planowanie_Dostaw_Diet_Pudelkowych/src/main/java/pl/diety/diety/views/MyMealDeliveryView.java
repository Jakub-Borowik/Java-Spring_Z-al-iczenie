package pl.diety.diety.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.diety.diety.model.MealDelivery;
import pl.diety.diety.service.MealDeliveryService;

@Route("meal-deliveries/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyMealDeliveryView extends VerticalLayout {
    public MyMealDeliveryView(MealDeliveryService service) {
        Grid<MealDelivery> grid = new Grid<>(MealDelivery.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Trasa");
        grid.addColumn(MealDelivery::getCategory).setHeader("Kategoria");
        grid.addColumn(MealDelivery::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(MealDelivery::getMinQuality).setHeader("Jakosc");
        grid.addColumn(MealDelivery::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(MealDelivery::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MealDelivery::getScore).setHeader("Wynik");
        grid.addColumn(MealDelivery::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("meal-deliveries", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
