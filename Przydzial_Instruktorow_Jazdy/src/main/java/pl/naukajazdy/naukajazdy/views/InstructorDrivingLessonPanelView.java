package pl.naukajazdy.naukajazdy.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.naukajazdy.naukajazdy.model.DrivingLesson;
import pl.naukajazdy.naukajazdy.service.DrivingLessonService;

@Route("instructor/driving-lessons")
@PageTitle("Panel")
@RolesAllowed("INSTRUCTOR")
public class InstructorDrivingLessonPanelView extends VerticalLayout {
    private final DrivingLessonService service;
    private final Grid<DrivingLesson> grid = new Grid<>(DrivingLesson.class, false);

    public InstructorDrivingLessonPanelView(DrivingLessonService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(DrivingLesson::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Instruktor");
        grid.addColumn(DrivingLesson::getCategory).setHeader("Kategoria");
        grid.addColumn(DrivingLesson::getStartTime).setHeader("Start");
        grid.addColumn(DrivingLesson::getEndTime).setHeader("Koniec");
        grid.addColumn(DrivingLesson::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(DrivingLesson::getScore).setHeader("Wynik");
        grid.addColumn(DrivingLesson::getTotalPrice).setHeader("Koszt");
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
