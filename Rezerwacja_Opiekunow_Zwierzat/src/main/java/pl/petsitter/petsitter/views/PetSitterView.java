package pl.petsitter.petsitter.views;

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
import pl.petsitter.petsitter.model.*;
import pl.petsitter.petsitter.service.PetCareBookingService;

@Route("pet-care")
@RouteAlias("")
@PageTitle("Opiekunowie zwierzat")
@RolesAllowed("CLIENT")
public class PetSitterView extends VerticalLayout {
    private final PetCareBookingService service;
    private final ComboBox<PetCareType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba zwierzat");
    private final IntegerField minQuality = new IntegerField("Minimalne doswiadczenie");
    private final IntegerField taskUnits = new IntegerField("Punkty opieki");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<PetSitter> grid = new Grid<>(PetSitter.class, false);

    public PetSitterView(PetCareBookingService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("pet-care/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Opiekunowie zwierzat"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(PetCareType.values());
        category.setValue(PetCareType.DOG);
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
        grid.addColumn(PetSitter::getName).setHeader("Nazwa");
        grid.addColumn(PetSitter::getCategory).setHeader("Kategoria");
        grid.addColumn(PetSitter::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(PetSitter::getQuality).setHeader("Jakosc");
        grid.addColumn(PetSitter::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(PetSitter::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(PetSitter::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            PetCareBooking record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
