package pl.pracownie.pracownie.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pracownie.pracownie.model.LabReservation;
import pl.pracownie.pracownie.service.LabReservationService;

@Route("teacher/school-labs")
@PageTitle("Panel")
@RolesAllowed("TEACHER")
public class TeacherLabReservationPanelView extends VerticalLayout {
    private final LabReservationService service;
    private final Grid<LabReservation> grid = new Grid<>(LabReservation.class, false);

    public TeacherLabReservationPanelView(LabReservationService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(LabReservation::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pracownia");
        grid.addColumn(LabReservation::getCategory).setHeader("Kategoria");
        grid.addColumn(LabReservation::getStartTime).setHeader("Start");
        grid.addColumn(LabReservation::getEndTime).setHeader("Koniec");
        grid.addColumn(LabReservation::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(LabReservation::getScore).setHeader("Wynik");
        grid.addColumn(LabReservation::getTotalPrice).setHeader("Koszt");
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
