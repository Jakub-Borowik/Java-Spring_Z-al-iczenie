package pl.pracownie.pracownie.views;

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
import pl.pracownie.pracownie.model.*;
import pl.pracownie.pracownie.service.LabReservationService;

@Route("school-labs")
@RouteAlias("")
@PageTitle("Pracownie szkolne")
@RolesAllowed("CLIENT")
public class SchoolLabView extends VerticalLayout {
    private final LabReservationService service;
    private final ComboBox<LabType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba uczniow");
    private final IntegerField minQuality = new IntegerField("Minimalny standard pracowni");
    private final IntegerField taskUnits = new IntegerField("Punkty zajec");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<SchoolLab> grid = new Grid<>(SchoolLab.class, false);

    public SchoolLabView(LabReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("school-labs/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Pracownie szkolne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(LabType.values());
        category.setValue(LabType.CHEMISTRY);
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
        grid.addColumn(SchoolLab::getName).setHeader("Nazwa");
        grid.addColumn(SchoolLab::getCategory).setHeader("Kategoria");
        grid.addColumn(SchoolLab::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(SchoolLab::getQuality).setHeader("Jakosc");
        grid.addColumn(SchoolLab::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(SchoolLab::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(SchoolLab::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            LabReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
