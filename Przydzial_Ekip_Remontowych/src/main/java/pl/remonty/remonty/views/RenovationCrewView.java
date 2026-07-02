package pl.remonty.remonty.views;

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
import pl.remonty.remonty.model.*;
import pl.remonty.remonty.service.RenovationOrderService;

@Route("renovations")
@RouteAlias("")
@PageTitle("Ekipy remontowe")
@RolesAllowed("CLIENT")
public class RenovationCrewView extends VerticalLayout {
    private final RenovationOrderService service;
    private final ComboBox<RenovationType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Metraz / zakres prac");
    private final IntegerField minQuality = new IntegerField("Minimalna klasa ekipy");
    private final IntegerField taskUnits = new IntegerField("Punkty pracy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<RenovationCrew> grid = new Grid<>(RenovationCrew.class, false);

    public RenovationCrewView(RenovationOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("renovations/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ekipy remontowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(RenovationType.values());
        category.setValue(RenovationType.PAINTING);
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
        grid.addColumn(RenovationCrew::getName).setHeader("Nazwa");
        grid.addColumn(RenovationCrew::getCategory).setHeader("Kategoria");
        grid.addColumn(RenovationCrew::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(RenovationCrew::getQuality).setHeader("Jakosc");
        grid.addColumn(RenovationCrew::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(RenovationCrew::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(RenovationCrew::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            RenovationOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
