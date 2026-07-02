package pl.ladowarki.ladowarki.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.ladowarki.ladowarki.model.ChargingSession;
import pl.ladowarki.ladowarki.service.ChargingSessionService;

@Route("charging-sessions/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyChargingSessionView extends VerticalLayout {
    public MyChargingSessionView(ChargingSessionService service) {
        Grid<ChargingSession> grid = new Grid<>(ChargingSession.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Ladowarka");
        grid.addColumn(ChargingSession::getCategory).setHeader("Kategoria");
        grid.addColumn(ChargingSession::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(ChargingSession::getMinQuality).setHeader("Jakosc");
        grid.addColumn(ChargingSession::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(ChargingSession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ChargingSession::getScore).setHeader("Wynik");
        grid.addColumn(ChargingSession::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("charging-sessions", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
