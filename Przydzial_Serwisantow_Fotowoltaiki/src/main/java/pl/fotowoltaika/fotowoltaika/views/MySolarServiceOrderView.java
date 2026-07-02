package pl.fotowoltaika.fotowoltaika.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.fotowoltaika.fotowoltaika.model.SolarServiceOrder;
import pl.fotowoltaika.fotowoltaika.service.SolarServiceOrderService;

@Route("solar-services/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MySolarServiceOrderView extends VerticalLayout {
    public MySolarServiceOrderView(SolarServiceOrderService service) {
        Grid<SolarServiceOrder> grid = new Grid<>(SolarServiceOrder.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Serwisant");
        grid.addColumn(SolarServiceOrder::getCategory).setHeader("Kategoria");
        grid.addColumn(SolarServiceOrder::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(SolarServiceOrder::getMinQuality).setHeader("Jakosc");
        grid.addColumn(SolarServiceOrder::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(SolarServiceOrder::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(SolarServiceOrder::getScore).setHeader("Wynik");
        grid.addColumn(SolarServiceOrder::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("solar-services", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
