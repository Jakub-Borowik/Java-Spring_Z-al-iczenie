package pl.okna.okna.views;

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
import pl.okna.okna.model.*;
import pl.okna.okna.service.WindowCleaningOrderService;

@Route("window-cleaning")
@RouteAlias("")
@PageTitle("Ekipy mycia okien")
@RolesAllowed("CLIENT")
public class WindowCleaningTeamView extends VerticalLayout {
    private final WindowCleaningOrderService service;
    private final ComboBox<WindowServiceType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba okien");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom sprzetu");
    private final IntegerField taskUnits = new IntegerField("Punkty wysokosci");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<WindowCleaningTeam> grid = new Grid<>(WindowCleaningTeam.class, false);

    public WindowCleaningTeamView(WindowCleaningOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("window-cleaning/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Ekipy mycia okien"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(WindowServiceType.values());
        category.setValue(WindowServiceType.FLAT);
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
        grid.addColumn(WindowCleaningTeam::getName).setHeader("Nazwa");
        grid.addColumn(WindowCleaningTeam::getCategory).setHeader("Kategoria");
        grid.addColumn(WindowCleaningTeam::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(WindowCleaningTeam::getQuality).setHeader("Jakosc");
        grid.addColumn(WindowCleaningTeam::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(WindowCleaningTeam::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(WindowCleaningTeam::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            WindowCleaningOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
