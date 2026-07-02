package pl.fotowoltaika.fotowoltaika.views;

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
import pl.fotowoltaika.fotowoltaika.model.*;
import pl.fotowoltaika.fotowoltaika.service.SolarServiceOrderService;

@Route("solar-services")
@RouteAlias("")
@PageTitle("Serwisanci fotowoltaiki")
@RolesAllowed("CLIENT")
public class SolarTechnicianView extends VerticalLayout {
    private final SolarServiceOrderService service;
    private final ComboBox<SolarServiceType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba paneli");
    private final IntegerField minQuality = new IntegerField("Minimalne uprawnienia");
    private final IntegerField taskUnits = new IntegerField("Punkty serwisu");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<SolarTechnician> grid = new Grid<>(SolarTechnician.class, false);

    public SolarTechnicianView(SolarServiceOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("solar-services/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Serwisanci fotowoltaiki"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SolarServiceType.values());
        category.setValue(SolarServiceType.INSPECTION);
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
        grid.addColumn(SolarTechnician::getName).setHeader("Nazwa");
        grid.addColumn(SolarTechnician::getCategory).setHeader("Kategoria");
        grid.addColumn(SolarTechnician::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(SolarTechnician::getQuality).setHeader("Jakosc");
        grid.addColumn(SolarTechnician::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(SolarTechnician::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(SolarTechnician::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            SolarServiceOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
