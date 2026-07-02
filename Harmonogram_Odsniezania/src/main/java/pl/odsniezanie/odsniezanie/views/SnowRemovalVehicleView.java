package pl.odsniezanie.odsniezanie.views;

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
import pl.odsniezanie.odsniezanie.model.*;
import pl.odsniezanie.odsniezanie.service.SnowRemovalOrderService;

@Route("snow-removal")
@RouteAlias("")
@PageTitle("Pojazdy odsniezania odsniezania")
@RolesAllowed("CLIENT")
public class SnowRemovalVehicleView extends VerticalLayout {
    private final SnowRemovalOrderService service;
    private final ComboBox<SnowZone> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Powierzchnia");
    private final IntegerField minQuality = new IntegerField("Minimalna moc sprzetu");
    private final IntegerField taskUnits = new IntegerField("Punkty trasy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<SnowRemovalVehicle> grid = new Grid<>(SnowRemovalVehicle.class, false);

    public SnowRemovalVehicleView(SnowRemovalOrderService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("snow-removal/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Pojazdy odsniezania odsniezania"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(SnowZone.values());
        category.setValue(SnowZone.SIDEWALK);
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
        grid.addColumn(SnowRemovalVehicle::getName).setHeader("Nazwa");
        grid.addColumn(SnowRemovalVehicle::getCategory).setHeader("Kategoria");
        grid.addColumn(SnowRemovalVehicle::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(SnowRemovalVehicle::getQuality).setHeader("Jakosc");
        grid.addColumn(SnowRemovalVehicle::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(SnowRemovalVehicle::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(SnowRemovalVehicle::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            SnowRemovalOrder record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
