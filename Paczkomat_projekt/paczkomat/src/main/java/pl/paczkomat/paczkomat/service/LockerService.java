package pl.paczkomat.paczkomat.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.paczkomat.paczkomat.model.Locker;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.repository.LockerRepository;
import pl.paczkomat.paczkomat.repository.ParcelRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class LockerService {
    private final LockerRepository lockerRepository;
    private final ParcelRepository parcelRepository;

    public Locker findSmallestLockerAvailable(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found."));

        return findSmallestLockerAvailable(parcel);
    }

    public Locker findSmallestLockerAvailable(Parcel parcel) {
        return lockerRepository.findAll().stream()
                .filter(locker -> !locker.isOccupied())
                .filter(locker -> !parcelRepository.existsByLockerId(locker.getId()))
                .filter(locker -> locker.getSize().ordinal() >= parcel.getSize().ordinal())
                .sorted(Comparator.comparing((Locker locker) -> locker.getSize().ordinal()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No locker available."));
    }

    public void occupyLocker(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new RuntimeException("Locker not found."));
        
        locker.setOccupied(true);
        lockerRepository.save(locker);
    }

    public void freeLocker(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new RuntimeException("Locker not found."));

        parcelRepository.findByLockerId(lockerId)
                .ifPresent(parcelRepository::delete);
        locker.setOccupied(false);
        lockerRepository.save(locker);
    }

    public List<Locker> getAllLockers() {
        return lockerRepository.findAll();
    }

    public List<Locker> getAvailableLockers() {
        return lockerRepository.findByOccupied(false);
    }
}
