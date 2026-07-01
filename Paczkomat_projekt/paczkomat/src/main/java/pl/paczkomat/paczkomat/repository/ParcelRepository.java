package pl.paczkomat.paczkomat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.paczkomat.paczkomat.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    
}
 