package pl.pielegniarki.pielegniarki.views;

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
import pl.pielegniarki.pielegniarki.model.*;
import pl.pielegniarki.pielegniarki.service.HomeVisitService;

@Route("home-visits")
@RouteAlias("")
@PageTitle("Zespoly pielegniarskie")
@RolesAllowed("CLIENT")
public class NurseTeamView extends VerticalLayout {
    private final HomeVisitService service;
    private final ComboBox<CareType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Zlozonosc wizyty");
    private final IntegerField minQuality = new IntegerField("Minimalny poziom uprawnien");
    private final IntegerField taskUnits = new IntegerField("Punkty opieki");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<NurseTeam> grid = new Grid<>(NurseTeam.class, false);

    public NurseTeamView(HomeVisitService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("home-visits/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Zespoly pielegniarskie"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(CareType.values());
        category.setValue(CareType.BASIC);
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
        grid.addColumn(NurseTeam::getName).setHeader("Nazwa");
        grid.addColumn(NurseTeam::getCategory).setHeader("Kategoria");
        grid.addColumn(NurseTeam::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(NurseTeam::getQuality).setHeader("Jakosc");
        grid.addColumn(NurseTeam::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(NurseTeam::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(NurseTeam::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            HomeVisit record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
