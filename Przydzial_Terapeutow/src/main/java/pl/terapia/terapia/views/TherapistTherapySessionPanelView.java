package pl.terapia.terapia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.terapia.terapia.model.TherapySession;
import pl.terapia.terapia.service.TherapySessionService;

@Route("therapist/therapy")
@PageTitle("Panel")
@RolesAllowed("THERAPIST")
public class TherapistTherapySessionPanelView extends VerticalLayout {
    private final TherapySessionService service;
    private final Grid<TherapySession> grid = new Grid<>(TherapySession.class, false);

    public TherapistTherapySessionPanelView(TherapySessionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(TherapySession::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Terapeuta");
        grid.addColumn(TherapySession::getCategory).setHeader("Kategoria");
        grid.addColumn(TherapySession::getStartTime).setHeader("Start");
        grid.addColumn(TherapySession::getEndTime).setHeader("Koniec");
        grid.addColumn(TherapySession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TherapySession::getScore).setHeader("Wynik");
        grid.addColumn(TherapySession::getTotalPrice).setHeader("Koszt");
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
