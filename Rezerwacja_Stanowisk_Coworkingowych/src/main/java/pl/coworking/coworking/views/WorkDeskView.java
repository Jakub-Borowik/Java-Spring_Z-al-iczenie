package pl.coworking.coworking.views;

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
import pl.coworking.coworking.model.*;
import pl.coworking.coworking.service.CoworkingBookingService;

@Route("coworking-bookings")
@RouteAlias("")
@PageTitle("Stanowiska coworkingowe")
@RolesAllowed("CLIENT")
public class WorkDeskView extends VerticalLayout {
    private final CoworkingBookingService service;
    private final ComboBox<DeskType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba osob");
    private final IntegerField minQuality = new IntegerField("Minimalny standard");
    private final IntegerField taskUnits = new IntegerField("Godziny pracy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<WorkDesk> grid = new Grid<>(WorkDesk.class, false);

    public WorkDeskView(CoworkingBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("coworking-bookings/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stanowiska coworkingowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(DeskType.values());
        category.setValue(DeskType.OPEN_SPACE);
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
        grid.addColumn(WorkDesk::getName).setHeader("Nazwa");
        grid.addColumn(WorkDesk::getCategory).setHeader("Kategoria");
        grid.addColumn(WorkDesk::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(WorkDesk::getQuality).setHeader("Jakosc");
        grid.addColumn(WorkDesk::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(WorkDesk::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(WorkDesk::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            CoworkingBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
