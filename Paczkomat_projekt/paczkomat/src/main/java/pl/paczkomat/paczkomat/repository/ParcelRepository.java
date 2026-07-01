package pl.paczkomat.paczkomat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.paczkomat.paczkomat.model.AppUser;
import pl.paczkomat.paczkomat.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByUser(AppUser user);

    Optional<Parcel> findByLockerId(Long lockerId);

    boolean existsByLockerId(Long lockerId);
}
 
