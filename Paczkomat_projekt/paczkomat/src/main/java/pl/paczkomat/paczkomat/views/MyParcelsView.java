package pl.paczkomat.paczkomat.views;

import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.service.ParcelService;

@Route("parcels/my")
@PageTitle("Moje paczki")
@RolesAllowed("CLIENT")
public class MyParcelsView extends VerticalLayout {
    private final ParcelService parcelService;
    private final Grid<Parcel> parcelGrid = new Grid<>(Parcel.class, false);

    public MyParcelsView(ParcelService parcelService) {
        this.parcelService = parcelService;

        configureGrid();

        Button refreshButton = new Button("Odswiez", event -> refreshParcels());
        HorizontalLayout navigation = new HorizontalLayout(new Anchor("", "Nadaj paczke"), refreshButton);

        add(new H2("Moje paczki"), navigation, parcelGrid);
        refreshParcels();
    }

    private void configureGrid() {
        parcelGrid.addColumn(Parcel::getId).setHeader("ID");
        parcelGrid.addColumn(Parcel::getSize).setHeader("Rozmiar paczki");
        parcelGrid.addColumn(parcel -> getLockerCode(parcel).orElse("-")).setHeader("Skrytka");
        parcelGrid.addColumn(parcel -> getLocker(parcel).map(Locker::getSize).map(Enum::name).orElse("-"))
                .setHeader("Rozmiar skrytki");
        parcelGrid.addColumn(parcel -> getLocker(parcel).map(Locker::isOccupied).orElse(false))
                .setHeader("Zajeta");
        parcelGrid.setWidthFull();
    }

    private void refreshParcels() {
        parcelGrid.setItems(parcelService.getLoggedInUserparcels());
    }

    private Optional<Locker> getLocker(Parcel parcel) {
        return Optional.ofNullable(parcel.getLocker());
    }

    private Optional<String> getLockerCode(Parcel parcel) {
        return getLocker(parcel).map(Locker::getCode);
    }
}
