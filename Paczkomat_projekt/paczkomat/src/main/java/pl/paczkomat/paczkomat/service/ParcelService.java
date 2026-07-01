package pl.paczkomat.paczkomat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.paczkomat.paczkomat.model.AppUser;
import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.model.Size;
import pl.paczkomat.paczkomat.repository.LockerRepository;
import pl.paczkomat.paczkomat.repository.ParcelRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final AppUserService appUserService;
    private final LockerService lockerService;
    private final LockerRepository lockerRepository;

    public Parcel createParcel(Size size) {
        AppUser user = appUserService.getLoggedInUser();
        Parcel parcel = new Parcel();
        parcel.setSize(size);
        parcel.setUser(user);

        Locker locker = lockerService.findSmallestLockerAvailable(parcel);
        parcel.setLocker(locker);
        locker.setOccupied(true);

        lockerRepository.save(locker);
        return parcelRepository.save(parcel);
    }

    public List<Parcel> getLoggedInUserparcels() {
        AppUser user = appUserService.getLoggedInUser();
        return parcelRepository.findByUser(user);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
}
