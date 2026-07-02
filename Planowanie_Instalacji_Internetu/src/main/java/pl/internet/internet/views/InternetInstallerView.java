package pl.internet.internet.views;

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
import pl.internet.internet.model.*;
import pl.internet.internet.service.InternetInstallationService;

@Route("internet-installations")
@RouteAlias("")
@PageTitle("Instalatorzy internetu")
@RolesAllowed("CLIENT")
public class InternetInstallerView extends VerticalLayout {
    private final InternetInstallationService service;
    private final ComboBox<InstallationType> category = new ComboBox<>("Kategoria");
    private final IntegerField requiredCapacity = new IntegerField("Liczba punktow");
    private final IntegerField minQuality = new IntegerField("Minimalna klasa certyfikatu");
    private final IntegerField taskUnits = new IntegerField("Punkty instalacji");
    private final IntegerField priority = new IntegerField("Priorytet 1-5");
    private final DateTimePicker startTime = new DateTimePicker("Start");
    private final DateTimePicker endTime = new DateTimePicker("Koniec");
    private final Grid<InternetInstaller> grid = new Grid<>(InternetInstaller.class, false);

    public InternetInstallerView(InternetInstallationService service) {
        this.service = service;
        configureForm();
        configureGrid();
        add(new HorizontalLayout(new Anchor("internet-installations/my", "Moje wpisy"), new Anchor("me", "Security info")),
                new H2("Instalatorzy internetu"),
                category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime,
                new Button("Utworz", event -> createRecord()),
                grid);
        refresh();
    }

    private void configureForm() {
        category.setItems(InstallationType.values());
        category.setValue(InstallationType.FIBER);
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
        grid.addColumn(InternetInstaller::getName).setHeader("Nazwa");
        grid.addColumn(InternetInstaller::getCategory).setHeader("Kategoria");
        grid.addColumn(InternetInstaller::getCapacity).setHeader("Pojemnosc");
        grid.addColumn(InternetInstaller::getQuality).setHeader("Jakosc");
        grid.addColumn(InternetInstaller::getUnitsPerHour).setHeader("Jedn./h");
        grid.addColumn(InternetInstaller::getPricePerHour).setHeader("Cena/h");
        grid.addColumn(InternetInstaller::isAvailable).setHeader("Dostepny");
        grid.setWidthFull();
    }

    private void createRecord() {
        try {
            InternetInstallation record = service.createRecord(category.getValue(), requiredCapacity.getValue(),
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
