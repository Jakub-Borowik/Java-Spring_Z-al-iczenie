package pl.okna.okna.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.okna.okna.model.WindowCleaningOrder;
import pl.okna.okna.service.WindowCleaningOrderService;

@Route("window-cleaning/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyWindowCleaningOrderView extends VerticalLayout {
    public MyWindowCleaningOrderView(WindowCleaningOrderService service) {
        Grid<WindowCleaningOrder> grid = new Grid<>(WindowCleaningOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ekipa");
        grid.addColumn(WindowCleaningOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(WindowCleaningOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(WindowCleaningOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(WindowCleaningOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(WindowCleaningOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(WindowCleaningOrder::getScore).setHeader("Wynik");
        grid.addColumn(WindowCleaningOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("window-cleaning", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
