package pl.paczkomat.paczkomat.service;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.repository.LockerRepository;
import pl.paczkomat.paczkomat.repository.ParcelRepository;

@RequiredArgsConstructor
@Service
public class LockerService {
    private final LockerRepository lockerRepository;
    private final ParcelRepository parcelRepository;

    public Locker findSmallestLockerAvailable(Parcel parcel) {
        Parcel foundParcel = parcelRepository.findById(parcel.getId())
        .orElseThrow(() -> new RuntimeException("Parcel not found."));

        return lockerRepository.findAll().stream()
        .filter(locker -> !locker.isOccupied())
        .filter(locker -> locker.getSize().ordinal() >= foundParcel.getSize().ordinal())
        .sorted(Comparator.comparing((Locker locker) -> locker.getSize().ordinal()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No locker available."));
    }
}
