package pl.ladowarki.ladowarki.views;

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
import pl.ladowarki.ladowarki.model.*;
import pl.ladowarki.ladowarki.service.ChargingSessionService;

@Route("charging-sessions")
@RouteAlias("")
@PageTitle("Ladowarki elektryczne")
@RolesAllowed("CLIENT")
public class ChargingStationView extends VerticalLayout {
    private final ChargingSessionService service;
    private final ComboBox<ChargingType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Wymagana moc");
    private final IntegerField minQuality = new IntegerField("Minimalny standard");
    private final IntegerField taskUnits = new IntegerField("kWh do naladowania");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<ChargingStation> grid = new Grid<>(ChargingStation.class, false);

    public ChargingStationView(ChargingSessionService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("charging-sessions/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ladowarki elektryczne"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(ChargingType.values());
        category.setValue(ChargingType.CITY);
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
        grid.addColumn(ChargingStation::getName).setHeader("Nazwa");
        grid.addColumn(ChargingStation::getCategory).setHeader("Kategoria");
        grid.addColumn(ChargingStation::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(ChargingStation::getQuality).setHeader("Jakosc");
        grid.addColumn(ChargingStation::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(ChargingStation::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(ChargingStation::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            ChargingSession record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
