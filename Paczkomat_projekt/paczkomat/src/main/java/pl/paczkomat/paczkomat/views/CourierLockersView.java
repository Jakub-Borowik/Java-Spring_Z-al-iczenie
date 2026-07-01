package pl.paczkomat.paczkomat.views;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.service.LockerService;

@Route("courier/lockers")
@PageTitle("Kurier - skrytki")
@RolesAllowed("COURIER")
public class CourierLockersView extends VerticalLayout {
    private final LockerService lockerService;
    private final Grid<Locker> lockerGrid = new Grid<>(Locker.class, false);

    public CourierLockersView(LockerService lockerService) {
        this.lockerService = lockerService;

        configureGrid();
        add(new H2("Kurier - skrytki"), lockerGrid);
        refreshLockers();
    }

    private void configureGrid() {
        lockerGrid.addColumn(Locker::getId).setHeader("ID");
        lockerGrid.addColumn(Locker::getCode).setHeader("Kod");
        lockerGrid.addColumn(Locker::getSize).setHeader("Rozmiar");
        lockerGrid.addComponentColumn(this::occupiedCheckbox).setHeader("Zajeta");
        lockerGrid.setWidthFull();
    }

    private Checkbox occupiedCheckbox(Locker locker) {
        Checkbox checkbox = new Checkbox();
        checkbox.setValue(locker.isOccupied());
        checkbox.addValueChangeListener(event -> updateLocker(locker, event.getValue()));
        return checkbox;
    }

    private void updateLocker(Locker locker, boolean occupied) {
        try {
            java.util.Optional.of(occupied)
                    .filter(Boolean::booleanValue)
                    .ifPresentOrElse(
                            value -> lockerService.occupyLocker(locker.getId()),
                            () -> lockerService.freeLocker(locker.getId()));
            refreshLockers();
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    private void refreshLockers() {
        lockerGrid.setItems(lockerService.getAllLockers());
    }
}
