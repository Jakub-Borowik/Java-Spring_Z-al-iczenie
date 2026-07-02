package pl.petsitter.petsitter.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.petsitter.petsitter.model.PetCareBooking;
import pl.petsitter.petsitter.service.PetCareBookingService;

@Route("sitter/pet-care")
@PageTitle("Panel")
@RolesAllowed("SITTER")
public class SitterPetCareBookingPanelView extends VerticalLayout {
    private final PetCareBookingService service;
    private final Grid<PetCareBooking> grid = new Grid<>(PetCareBooking.class, false);

    public SitterPetCareBookingPanelView(PetCareBookingService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(PetCareBooking::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Opiekun");
        grid.addColumn(PetCareBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(PetCareBooking::getStartTime).setHeader("Start");
        grid.addColumn(PetCareBooking::getEndTime).setHeader("Koniec");
        grid.addColumn(PetCareBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PetCareBooking::getScore).setHeader("Wynik");
        grid.addColumn(PetCareBooking::getTotalPrice).setHeader("Koszt");
        grid.addComponentColumn(record -> new Button("Usun", event -> {
            service.cancelRecord(record.getId());
            refresh();
        }));
        grid.setWidthFull();
    }

    private void refresh() {
        grid.setItems(service.getAllRecords());
    }
}
