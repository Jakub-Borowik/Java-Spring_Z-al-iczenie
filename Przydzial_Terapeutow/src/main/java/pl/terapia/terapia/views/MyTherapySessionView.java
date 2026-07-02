package pl.terapia.terapia.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.terapia.terapia.model.TherapySession;
import pl.terapia.terapia.service.TherapySessionService;

@Route("therapy/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyTherapySessionView extends VerticalLayout {
    public MyTherapySessionView(TherapySessionService service) {
        Grid<TherapySession> grid = new Grid<>(TherapySession.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Terapeuta");
        grid.addColumn(TherapySession::getCategory).setHeader("Kategoria");
        grid.addColumn(TherapySession::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(TherapySession::getMinQuality).setHeader("Jakosc");
        grid.addColumn(TherapySession::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(TherapySession::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(TherapySession::getScore).setHeader("Wynik");
        grid.addColumn(TherapySession::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("therapy", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
