package pl.egzaminy.egzaminy.views;

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
import pl.egzaminy.egzaminy.model.*;
import pl.egzaminy.egzaminy.service.ExamReservationService;

@Route("exam-rooms")
@RouteAlias("")
@PageTitle("Sale egzaminacyjne")
@RolesAllowed("CLIENT")
public class ExamRoomView extends VerticalLayout {
    private final ExamReservationService service;
    private final ComboBox<ExamType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba studentow");
    private final IntegerField minQuality = new IntegerField("Minimalny standard sali");
    private final IntegerField taskUnits = new IntegerField("Punkty egzaminu");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<ExamRoom> grid = new Grid<>(ExamRoom.class, false);

    public ExamRoomView(ExamReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("exam-rooms/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Sale egzaminacyjne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(ExamType.values());
        category.setValue(ExamType.WRITTEN);
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
        grid.addColumn(ExamRoom::getName).setHeader("Nazwa");
        grid.addColumn(ExamRoom::getCategory).setHeader("Kategoria");
        grid.addColumn(ExamRoom::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(ExamRoom::getQuality).setHeader("Jakosc");
        grid.addColumn(ExamRoom::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(ExamRoom::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(ExamRoom::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            ExamReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
