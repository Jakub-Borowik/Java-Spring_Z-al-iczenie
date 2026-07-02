package pl.transportmed.transportmed.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.transportmed.transportmed.model.MedicalTransport;
import pl.transportmed.transportmed.service.MedicalTransportService;

@Route("dispatcher/medical-transport")
@PageTitle("Panel")
@RolesAllowed("DISPATCHER")
public class DispatcherMedicalTransportPanelView extends VerticalLayout {
    private final MedicalTransportService service;
    private final Grid<MedicalTransport> grid = new Grid<>(MedicalTransport.class, false);

    public DispatcherMedicalTransportPanelView(MedicalTransportService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(MedicalTransport::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(MedicalTransport::getCategory).setHeader("Kategoria");
        grid.addColumn(MedicalTransport::getStartTime).setHeader("Start");
        grid.addColumn(MedicalTransport::getEndTime).setHeader("Koniec");
        grid.addColumn(MedicalTransport::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MedicalTransport::getScore).setHeader("Wynik");
        grid.addColumn(MedicalTransport::getTotalPrice).setHeader("Koszt");
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
