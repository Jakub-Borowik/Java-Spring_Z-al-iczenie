package pl.diety.diety.views;

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
import pl.diety.diety.model.*;
import pl.diety.diety.service.MealDeliveryService;

@Route("meal-deliveries")
@RouteAlias("")
@PageTitle("Trasy diet pudelkowych")
@RolesAllowed("CLIENT")
public class MealRouteView extends VerticalLayout {
    private final MealDeliveryService service;
    private final ComboBox<DietArea> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba adresow");
    private final IntegerField minQuality = new IntegerField("Minimalna jakosc termoboxow");
    private final IntegerField taskUnits = new IntegerField("Punkty dostawy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<MealRoute> grid = new Grid<>(MealRoute.class, false);

    public MealRouteView(MealDeliveryService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("meal-deliveries/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Trasy diet pudelkowych"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(DietArea.values());
        category.setValue(DietArea.CENTER);
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
        grid.addColumn(MealRoute::getName).setHeader("Nazwa");
        grid.addColumn(MealRoute::getCategory).setHeader("Kategoria");
        grid.addColumn(MealRoute::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(MealRoute::getQuality).setHeader("Jakosc");
        grid.addColumn(MealRoute::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(MealRoute::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(MealRoute::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            MealDelivery record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
