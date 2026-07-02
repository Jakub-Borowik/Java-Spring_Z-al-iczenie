package pl.przeglady.przeglady.views;

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
import pl.przeglady.przeglady.model.*;
import pl.przeglady.przeglady.service.VehicleInspectionService;

@Route("inspections")
@RouteAlias("")
@PageTitle("Stanowiska przegladow")
@RolesAllowed("CLIENT")
public class InspectionStationView extends VerticalLayout {
    private final VehicleInspectionService service;
    private final ComboBox<InspectionType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Masa stanowisko");
    private final IntegerField minQuality = new IntegerField("Minimalna klasa diagnosty");
    private final IntegerField taskUnits = new IntegerField("Punkty kontroli");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<InspectionStation> grid = new Grid<>(InspectionStation.class, false);

    public InspectionStationView(VehicleInspectionService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("inspections/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Stanowiska przegladow"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(InspectionType.values());
        category.setValue(InspectionType.CAR);
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
        grid.addColumn(InspectionStation::getName).setHeader("Nazwa");
        grid.addColumn(InspectionStation::getCategory).setHeader("Kategoria");
        grid.addColumn(InspectionStation::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(InspectionStation::getQuality).setHeader("Jakosc");
        grid.addColumn(InspectionStation::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(InspectionStation::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(InspectionStation::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            VehicleInspection record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
