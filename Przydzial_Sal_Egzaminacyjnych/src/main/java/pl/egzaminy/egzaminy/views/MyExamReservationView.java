package pl.egzaminy.egzaminy.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.egzaminy.egzaminy.model.ExamReservation;
import pl.egzaminy.egzaminy.service.ExamReservationService;

@Route("exam-rooms/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyExamReservationView extends VerticalLayout {
    public MyExamReservationView(ExamReservationService service) {
        Grid<ExamReservation> grid = new Grid<>(ExamReservation.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Sala");
        grid.addColumn(ExamReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(ExamReservation::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(ExamReservation::getMinQuality).setHeader("Jakosc");
        grid.addColumn(ExamReservation::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(ExamReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ExamReservation::getScore).setHeader("Wynik");
        grid.addColumn(ExamReservation::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("exam-rooms", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
