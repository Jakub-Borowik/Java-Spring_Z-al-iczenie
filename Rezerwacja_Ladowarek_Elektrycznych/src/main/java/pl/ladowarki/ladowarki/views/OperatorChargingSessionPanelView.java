package pl.ladowarki.ladowarki.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.ladowarki.ladowarki.model.ChargingSession;
import pl.ladowarki.ladowarki.service.ChargingSessionService;

@Route("operator/charging-sessions")
@PageTitle("Panel")
@RolesAllowed("OPERATOR")
public class OperatorChargingSessionPanelView extends VerticalLayout {
    private final ChargingSessionService service;
    private final Grid<ChargingSession> grid = new Grid<>(ChargingSession.class, false);

    public OperatorChargingSessionPanelView(ChargingSessionService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(ChargingSession::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ladowarka");
        grid.addColumn(ChargingSession::getCategory).setHeader("Kategoria");
        grid.addColumn(ChargingSession::getStartTime).setHeader("Start");
        grid.addColumn(ChargingSession::getEndTime).setHeader("Koniec");
        grid.addColumn(ChargingSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ChargingSession::getScore).setHeader("Wynik");
        grid.addColumn(ChargingSession::getTotalPrice).setHeader("Koszt");
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
