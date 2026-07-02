package pl.internet.internet.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.internet.internet.model.InternetInstallation;
import pl.internet.internet.service.InternetInstallationService;

@Route("technician/internet-installations")
@PageTitle("Panel")
@RolesAllowed("TECHNICIAN")
public class TechnicianInternetInstallationPanelView extends VerticalLayout {
    private final InternetInstallationService service;
    private final Grid<InternetInstallation> grid = new Grid<>(InternetInstallation.class, false);

    public TechnicianInternetInstallationPanelView(InternetInstallationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(InternetInstallation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Instalator");
        grid.addColumn(InternetInstallation::getCategory).setHeader("Kategoria");
        grid.addColumn(InternetInstallation::getStartTime).setHeader("Start");
        grid.addColumn(InternetInstallation::getEndTime).setHeader("Koniec");
        grid.addColumn(InternetInstallation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(InternetInstallation::getScore).setHeader("Wynik");
        grid.addColumn(InternetInstallation::getTotalPrice).setHeader("Koszt");
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
