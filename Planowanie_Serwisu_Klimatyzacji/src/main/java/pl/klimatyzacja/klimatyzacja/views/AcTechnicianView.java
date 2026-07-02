package pl.klimatyzacja.klimatyzacja.views;

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
import pl.klimatyzacja.klimatyzacja.model.*;
import pl.klimatyzacja.klimatyzacja.service.AcServiceOrderService;

@Route("ac-services")
@RouteAlias("")
@PageTitle("Technicy klimatyzacji")
@RolesAllowed("CLIENT")
public class AcTechnicianView extends VerticalLayout {
    private final AcServiceOrderService service;
    private final ComboBox<AcServiceType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba urzadzen");
    private final IntegerField minQuality = new IntegerField("Minimalne uprawnienia");
    private final IntegerField taskUnits = new IntegerField("Punkty serwisu");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<AcTechnician> grid = new Grid<>(AcTechnician.class, false);

    public AcTechnicianView(AcServiceOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("ac-services/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Technicy klimatyzacji"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(AcServiceType.values());
        category.setValue(AcServiceType.CLEANING);
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
        grid.addColumn(AcTechnician::getName).setHeader("Nazwa");
        grid.addColumn(AcTechnician::getCategory).setHeader("Kategoria");
        grid.addColumn(AcTechnician::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(AcTechnician::getQuality).setHeader("Jakosc");
        grid.addColumn(AcTechnician::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(AcTechnician::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(AcTechnician::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            AcServiceOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
