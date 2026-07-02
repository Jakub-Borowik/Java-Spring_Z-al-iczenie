package pl.transportmed.transportmed.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.transportmed.transportmed.model.MedicalTransport;
import pl.transportmed.transportmed.service.MedicalTransportService;

@Route("medical-transport/my")
@PageTitle("Moje wpisy")
@RolesAllowed("CLIENT")
public class MyMedicalTransportView extends VerticalLayout {
    public MyMedicalTransportView(MedicalTransportService service) {
        Grid<MedicalTransport> grid = new Grid<>(MedicalTransport.class, false);
        grid.addColumn(record -> record.getResource().getName()).setHeader("Pojazd");
        grid.addColumn(MedicalTransport::getCategory).setHeader("Kategoria");
        grid.addColumn(MedicalTransport::getRequiredCapacity).setHeader("Pojemnosc");
        grid.addColumn(MedicalTransport::getMinQuality).setHeader("Jakosc");
        grid.addColumn(MedicalTransport::getTaskUnits).setHeader("Jednostki");
        grid.addColumn(MedicalTransport::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(MedicalTransport::getScore).setHeader("Wynik");
        grid.addColumn(MedicalTransport::getTotalPrice).setHeader("Koszt");
        grid.setItems(service.getMine());
        add(new Anchor("medical-transport", "Panel klienta"), new H2("Moje wpisy"), grid);
    }
}
