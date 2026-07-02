package pl.internet.internet.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.internet.internet.model.InternetInstallation;
import pl.internet.internet.service.InternetInstallationService;

@Route("internet-installations/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyInternetInstallationView extends VerticalLayout {
    public MyInternetInstallationView(InternetInstallationService service) {
        Grid<InternetInstallation> grid = new Grid<>(InternetInstallation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Instalator");
        grid.addColumn(InternetInstallation::getCategory).setHeader("Kategoria");
        grid.addColumn(InternetInstallation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(InternetInstallation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(InternetInstallation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(InternetInstallation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(InternetInstallation::getScore).setHeader("Wynik");
        grid.addColumn(InternetInstallation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("internet-installations", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
