package pl.naukajazdy.naukajazdy.views;

import java.time.LocalDateTime;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.naukajazdy.naukajazdy.model.*;
import pl.naukajazdy.naukajazdy.service.DrivingLessonService;

@Route("driving-lessons")
@RouteAlias("")
@PageTitle("Instruktorzy jazdy")
@RolesAllowed("CLIENT")
public class DrivingInstructorView extends VerticalLayout {
    private final DrivingLessonService service;
    private final ComboBox<LessonType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Poziom kursanta");
    private final IntegerField minQuality = new IntegerField("Minimalne doswiadczenie");
    private final IntegerField taskUnits = new IntegerField("Punkty manewrow");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<DrivingInstructor> grid = new Grid<>(DrivingInstructor.class, false);

    public DrivingInstructorView(DrivingLessonService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("driving-lessons/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Instruktorzy jazdy"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(LessonType.values());
        category.setValue(LessonType.CITY);
        requiredCapacity.setMin(1);
        requiredCapacity.setValue(1);
        minQuality.setMin(1);
        minQuality.setValue(1);
        taskUnits.setMin(1);
        taskUnits.setValue(5);
        priority.setMin(1);
        priority.setMax(5);
        priority.setValue(3);
        startTime.setValue(LocalDateTime.now().plusDays(1));
        endTime.setValue(LocalDateTime.now().plusDays(1).plusHours(3));
    }

    private void configureGrid() {
        grid.addColumn(DrivingInstructor::getName).setHeader("Nazwa");
        grid.addColumn(DrivingInstructor::getCategory).setHeader("Kategoria");
        grid.addColumn(DrivingInstructor::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(DrivingInstructor::getQuality).setHeader("Jakosc");
        grid.addColumn(DrivingInstructor::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(DrivingInstructor::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(DrivingInstructor::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            DrivingLesson record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
                    minQuality.getValue(), taskUnits.getValue(), priority.getValue(),
                    startTime.getValue(), endTime.getValue());
            Notification.show("Utworzono id: " + record.getId() + ", koszt: " + record.getTotalPrice()
                    + ", wynik: " + record.getScore());
            refresh();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage());
        }
    }

    private void refresh() {
        grid.setItems(service.getAllResources());
    }
}
