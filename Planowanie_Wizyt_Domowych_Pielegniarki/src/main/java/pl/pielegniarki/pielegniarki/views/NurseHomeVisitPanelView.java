package pl.pielegniarki.pielegniarki.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import pl.pielegniarki.pielegniarki.model.HomeVisit;
import pl.pielegniarki.pielegniarki.service.HomeVisitService;

@Route("nurse/home-visits")
@PageTitle("Panel")
@RolesAllowed("NURSE")
public class NurseHomeVisitPanelView extends VerticalLayout {
    private final HomeVisitService service;
    private final Grid<HomeVisit> grid = new Grid<>(HomeVisit.class, false);

    public NurseHomeVisitPanelView(HomeVisitService service) {
        this.service = service;
        configureGrid();
        add(new Anchor("me", "Security info"), new H2("Panel roli wyzszej"), grid);
        refresh();
    }

    private void configureGrid() {
        grid.addColumn(HomeVisit::getId).setHeader("ID");
        grid.addColumn(record -> record.getUser().getLogin()).setHeader("Klient");
        grid.addColumn(record -> record.getResource().getName()).setHeader("Zespol opieki");
        grid.addColumn(HomeVisit::getCategory).setHeader("Kategoria");
        grid.addColumn(HomeVisit::getStartTime).setHeader("Start");
        grid.addColumn(HomeVisit::getEndTime).setHeader("Koniec");
        grid.addColumn(HomeVisit::getCalculatedHours).setHeader("Godziny");
        grid.addColumn(HomeVisit::getScore).setHeader("Wynik");
        grid.addColumn(HomeVisit::getTotalPrice).setHeader("Koszt");
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
