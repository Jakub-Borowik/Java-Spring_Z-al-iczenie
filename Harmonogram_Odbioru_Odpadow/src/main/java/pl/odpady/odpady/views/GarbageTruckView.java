package pl.odpady.odpady.views;

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
import pl.odpady.odpady.model.*;
import pl.odpady.odpady.service.WastePickupService;

@Route("waste-pickups")
@RouteAlias("")
@PageTitle("Pojazdy do odbioru odpadow do odbioru odpadow")
@RolesAllowed("CLIENT")
public class GarbageTruckView extends VerticalLayout {
    private final WastePickupService service;
    private final ComboBox<WasteType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba pojemnikow");
    private final IntegerField minQuality = new IntegerField("Minimalna klasa sanitarna");
    private final IntegerField taskUnits = new IntegerField("Punkty trasy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<GarbageTruck> grid = new Grid<>(GarbageTruck.class, false);

    public GarbageTruckView(WastePickupService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("waste-pickups/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Pojazdy do odbioru odpadow do odbioru odpadow"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(WasteType.values());
        category.setValue(WasteType.MIXED);
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
        grid.addColumn(GarbageTruck::getName).setHeader("Nazwa");
        grid.addColumn(GarbageTruck::getCategory).setHeader("Kategoria");
        grid.addColumn(GarbageTruck::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(GarbageTruck::getQuality).setHeader("Jakosc");
        grid.addColumn(GarbageTruck::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(GarbageTruck::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(GarbageTruck::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            WastePickup record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
