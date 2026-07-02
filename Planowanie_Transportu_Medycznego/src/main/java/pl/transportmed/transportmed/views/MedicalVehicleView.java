package pl.transportmed.transportmed.views;

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
import pl.transportmed.transportmed.model.*;
import pl.transportmed.transportmed.service.MedicalTransportService;

@Route("medical-transport")
@RouteAlias("")
@PageTitle("Transport medyczny")
@RolesAllowed("CLIENT")
public class MedicalVehicleView extends VerticalLayout {
    private final MedicalTransportService service;
    private final ComboBox<TransportType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Wymagany poziom wsparcia");
    private final IntegerField minQuality = new IntegerField("Minimalny standard medyczny");
    private final IntegerField taskUnits = new IntegerField("Kilometry trasy");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<MedicalVehicle> grid = new Grid<>(MedicalVehicle.class, false);

    public MedicalVehicleView(MedicalTransportService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("medical-transport/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Transport medyczny"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(TransportType.values());
        category.setValue(TransportType.SITTING);
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
        grid.addColumn(MedicalVehicle::getName).setHeader("Nazwa");
        grid.addColumn(MedicalVehicle::getCategory).setHeader("Kategoria");
        grid.addColumn(MedicalVehicle::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(MedicalVehicle::getQuality).setHeader("Jakosc");
        grid.addColumn(MedicalVehicle::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(MedicalVehicle::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(MedicalVehicle::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            MedicalTransport record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
