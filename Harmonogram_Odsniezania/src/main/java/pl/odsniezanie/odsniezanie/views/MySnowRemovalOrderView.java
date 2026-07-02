package pl.odsniezanie.odsniezanie.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.odsniezanie.odsniezanie.model.SnowRemovalOrder;
import pl.odsniezanie.odsniezanie.service.SnowRemovalOrderService;

@Route("snow-removal/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MySnowRemovalOrderView extends VerticalLayout {
    public MySnowRemovalOrderView(SnowRemovalOrderService service) {
        Grid<SnowRemovalOrder> grid = new Grid<>(SnowRemovalOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(SnowRemovalOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(SnowRemovalOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(SnowRemovalOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(SnowRemovalOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(SnowRemovalOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SnowRemovalOrder::getScore).setHeader("Wynik");
        grid.addColumn(SnowRemovalOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("snow-removal", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
