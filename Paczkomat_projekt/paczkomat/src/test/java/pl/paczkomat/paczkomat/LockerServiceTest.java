package pl.paczkomat.paczkomat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.model.Size;
import pl.paczkomat.paczkomat.repository.AppUserRepository;
import pl.paczkomat.paczkomat.repository.LockerRepository;
import pl.paczkomat.paczkomat.repository.ParcelRepository;
import pl.paczkomat.paczkomat.service.LockerService;

@ExtendWith(MockitoExtension.class)
public class LockerServiceTest {
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private LockerRepository lockerRepository;
    @Mock
    private ParcelRepository parcelRepository;
    @InjectMocks
    private LockerService lockerService;

    private Parcel parcel(Size size) {
        Parcel parcel = new Parcel();
        parcel.setSize(size);
        return parcel;
    }

    private Locker locker(Size size, boolean occupied) {
        Locker locker = new Locker();
        locker.setSize(size);
        locker.setOccupied(occupied);
        return locker;
    }

    @Test
    void shouldGiveSmallLockerToSmallParcel() {
        when(lockerRepository.findAll()).thenReturn(List.of(
                locker(Size.SMALL, false),
                locker(Size.MEDIUM, false),
                locker(Size.LARGE, false)));
        Locker result = lockerService.findSmallestLockerAvailable(parcel(Size.SMALL));

        assertLockerSizeEquals(Size.SMALL, result.getSize());
    }

    @Test
    void shouldGiveMediumLockerToSmallParcel() {
        when(lockerRepository.findAll()).thenReturn(List.of(
                locker(Size.SMALL, true),
                locker(Size.MEDIUM, false),
                locker(Size.LARGE, false)));
        Locker result = lockerService.findSmallestLockerAvailable(parcel(Size.SMALL));

        assertLockerSizeEquals(Size.MEDIUM, result.getSize());
    }

    @Test
    void shouldGiveMediumLockerToMediumParcel() {
        when(lockerRepository.findAll()).thenReturn(List.of(
                locker(Size.SMALL, false),
                locker(Size.MEDIUM, false),
                locker(Size.LARGE, false)));
        Locker result = lockerService.findSmallestLockerAvailable(parcel(Size.MEDIUM));

        assertLockerSizeEquals(Size.MEDIUM, result.getSize());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenNoLockerAvailable() {
        when(lockerRepository.findAll()).thenReturn(List.of(
                locker(Size.SMALL, true),
                locker(Size.MEDIUM, true),
                locker(Size.LARGE, true)));

        assertThrows(RuntimeException.class,
                () -> lockerService.findSmallestLockerAvailable(parcel(Size.MEDIUM)));
    }

    private void assertLockerSizeEquals(Size expected, Size actual) {
        assertEquals(expected, actual);
    }
}
