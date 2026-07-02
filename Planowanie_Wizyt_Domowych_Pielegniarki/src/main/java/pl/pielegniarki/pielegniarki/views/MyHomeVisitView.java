package pl.pielegniarki.pielegniarki.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pielegniarki.pielegniarki.model.HomeVisit;
import pl.pielegniarki.pielegniarki.service.HomeVisitService;

@Route("home-visits/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyHomeVisitView extends VerticalLayout {
    public MyHomeVisitView(HomeVisitService service) {
        Grid<HomeVisit> grid = new Grid<>(HomeVisit.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zespol opieki");
        grid.addColumn(HomeVisit::getCategory).setHeader("Kategoria");
        grid.addColumn(HomeVisit::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(HomeVisit::getMinQuality).setHeader("Jakosc");
        grid.addColumn(HomeVisit::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(HomeVisit::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(HomeVisit::getScore).setHeader("Wynik");
        grid.addColumn(HomeVisit::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("home-visits", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
