package pl.magazyn.magazyn.views;

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
import pl.magazyn.magazyn.model.*;
import pl.magazyn.magazyn.service.StorageReservationService;

@Route("storage")
@RouteAlias("")
@PageTitle("Boxy magazynowe")
@RolesAllowed("CLIENT")
public class StorageBoxView extends VerticalLayout {
    private final StorageReservationService service;
    private final ComboBox<StorageType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Objetosc rzeczy");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom ochrony");
    private final IntegerField taskUnits = new IntegerField("Dni skladowania");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<StorageBox> grid = new Grid<>(StorageBox.class, false);

    public StorageBoxView(StorageReservationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("storage/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Boxy magazynowe"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(StorageType.values());
        category.setValue(StorageType.SMALL);
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
        grid.addColumn(StorageBox::getName).setHeader("Nazwa");
        grid.addColumn(StorageBox::getCategory).setHeader("Kategoria");
        grid.addColumn(StorageBox::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(StorageBox::getQuality).setHeader("Jakosc");
        grid.addColumn(StorageBox::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(StorageBox::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(StorageBox::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            StorageReservation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
