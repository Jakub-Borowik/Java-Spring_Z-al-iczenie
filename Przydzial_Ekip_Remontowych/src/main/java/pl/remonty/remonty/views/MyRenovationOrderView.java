package pl.remonty.remonty.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.remonty.remonty.model.RenovationOrder;
import pl.remonty.remonty.service.RenovationOrderService;

@Route("renovations/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyRenovationOrderView extends VerticalLayout {
    public MyRenovationOrderView(RenovationOrderService service) {
        Grid<RenovationOrder> grid = new Grid<>(RenovationOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ekipa");
        grid.addColumn(RenovationOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(RenovationOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(RenovationOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(RenovationOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(RenovationOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(RenovationOrder::getScore).setHeader("Wynik");
        grid.addColumn(RenovationOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("renovations", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
