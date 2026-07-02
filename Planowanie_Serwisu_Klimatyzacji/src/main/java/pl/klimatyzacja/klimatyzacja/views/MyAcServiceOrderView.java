package pl.klimatyzacja.klimatyzacja.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.klimatyzacja.klimatyzacja.model.AcServiceOrder;
import pl.klimatyzacja.klimatyzacja.service.AcServiceOrderService;

@Route("ac-services/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyAcServiceOrderView extends VerticalLayout {
    public MyAcServiceOrderView(AcServiceOrderService service) {
        Grid<AcServiceOrder> grid = new Grid<>(AcServiceOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Technik");
        grid.addColumn(AcServiceOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(AcServiceOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(AcServiceOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(AcServiceOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(AcServiceOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(AcServiceOrder::getScore).setHeader("Wynik");
        grid.addColumn(AcServiceOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("ac-services", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
