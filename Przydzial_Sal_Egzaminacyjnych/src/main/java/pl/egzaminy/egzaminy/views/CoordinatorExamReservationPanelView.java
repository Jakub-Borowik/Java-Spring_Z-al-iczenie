package pl.egzaminy.egzaminy.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.egzaminy.egzaminy.model.ExamReservation;
import pl.egzaminy.egzaminy.service.ExamReservationService;

@Route("coordinator/exam-rooms")
@PageTitle("Panel")
@RolesAllowed("COORDINATOR")
public class CoordinatorExamReservationPanelView extends VerticalLayout {
    private final ExamReservationService service;
    private final Grid<ExamReservation> grid = new Grid<>(ExamReservation.class, false);

    public CoordinatorExamReservationPanelView(ExamReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(ExamReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Sala");
        grid.addColumn(ExamReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(ExamReservation::getStartTime).setHeader("Start");
        grid.addColumn(ExamReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(ExamReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(ExamReservation::getScore).setHeader("Wynik");
        grid.addColumn(ExamReservation::getTotalPrice).setHeader("Koszt");
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
