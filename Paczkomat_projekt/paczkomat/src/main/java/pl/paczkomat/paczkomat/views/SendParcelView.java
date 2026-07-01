package pl.paczkomat.paczkomat.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.model.Size;
import pl.paczkomat.paczkomat.service.ParcelService;

@Route("")
@PageTitle("Nadaj paczke")
@RolesAllowed("CLIENT")
public class SendParcelView extends VerticalLayout implements BeforeEnterObserver {
    private final ParcelService parcelService;
    private final Select<Size> sizeSelect = new Select<>();

    public SendParcelView(ParcelService parcelService) {
        this.parcelService = parcelService;

        sizeSelect.setLabel("Rozmiar paczki");
        sizeSelect.setItems(Size.values());
        sizeSelect.setValue(Size.SMALL);

        Button sendButton = new Button("Nadaj paczke", event -> sendParcel());
        HorizontalLayout navigation = new HorizontalLayout(new Anchor("parcels/my", "Moje paczki"));

        add(new H2("Paczkomat - nadanie paczki"), navigation, sizeSelect, sendButton);
    }

    private void sendParcel() {
        try {
            Parcel parcel = parcelService.createParcel(sizeSelect.getValue());
            Notification.show("Paczka nadana. ID: " + parcel.getId()
                    + ", skrytka: " + parcel.getLocker().getCode(), 4000, Notification.Position.TOP_CENTER);
        } catch (RuntimeException exception) {
            Notification.show(exception.getMessage(), 4000, Notification.Position.TOP_CENTER);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        java.util.Optional.of(isClient())
                .filter(Boolean::booleanValue)
                .orElseGet(() -> {
                    event.rerouteTo(CourierLockersView.class);
                    return false;
                });
    }

    private boolean isClient() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_CLIENT".equals(authority.getAuthority()));
    }
}
