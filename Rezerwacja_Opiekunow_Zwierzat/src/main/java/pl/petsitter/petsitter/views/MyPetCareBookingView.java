package pl.petsitter.petsitter.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.petsitter.petsitter.model.PetCareBooking;
import pl.petsitter.petsitter.service.PetCareBookingService;

@Route("pet-care/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyPetCareBookingView extends VerticalLayout {
    public MyPetCareBookingView(PetCareBookingService service) {
        Grid<PetCareBooking> grid = new Grid<>(PetCareBooking.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Opiekun");
        grid.addColumn(PetCareBooking::getCategory).setHeader("Kategoria");
        grid.addColumn(PetCareBooking::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(PetCareBooking::getMinQuality).setHeader("Jakosc");
        grid.addColumn(PetCareBooking::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(PetCareBooking::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(PetCareBooking::getScore).setHeader("Wynik");
        grid.addColumn(PetCareBooking::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("pet-care", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
