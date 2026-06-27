package com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByCarIdAndEndDateGreaterThanEqual(Long carId, LocalDate date);
}
