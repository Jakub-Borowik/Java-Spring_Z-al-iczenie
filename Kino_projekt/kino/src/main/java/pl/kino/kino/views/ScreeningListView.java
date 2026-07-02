package pl.kino.kino.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import pl.kino.kino.model.Screening;
import pl.kino.kino.service.ScreeningService;

@Route("")
@RouteAlias("screenings")
@PageTitle("Seanse")
@RolesAllowed("CLIENT")
public class ScreeningListView extends VerticalLayout {
    private final ScreeningService screeningService;
    private final Grid<Screening> screeningGrid = new Grid<>(Screening.class, false);

    public ScreeningListView(ScreeningService screeningService) {
        this.screeningService = screeningService;

        configureGrid();
        add(new H2("Seanse"), new HorizontalLayout(new Anchor("screenings/my-reservations", "Moje rezerwacje")),
                screeningGrid);
        refreshScreenings();
    }

    private void configureGrid() {
        screeningGrid.addColumn(Screening::getMovieTitle).setHeader("Film");
        screeningGrid.addColumn(Screening::getStartTime).setHeader("Start");
        screeningGrid.addColumn(screening -> screening.getHall().getName()).setHeader("Sala");
        screeningGrid.addComponentColumn(screening -> new Button("Wybierz",
                event -> getUI().ifPresent(ui -> ui.navigate("screenings/" + screening.getId()))));
        screeningGrid.setWidthFull();
    }

    private void refreshScreenings() {
        screeningGrid.setItems(screeningService.getAllScreenings());
    }
}
