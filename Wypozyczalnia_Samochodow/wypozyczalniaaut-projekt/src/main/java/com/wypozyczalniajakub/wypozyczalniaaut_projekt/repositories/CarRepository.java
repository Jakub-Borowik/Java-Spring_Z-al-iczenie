package com.wypozyczalniajakub.wypozyczalniaaut_projekt.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wypozyczalniajakub.wypozyczalniaaut_projekt.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.available = true AND c.id NOT IN (" +
           "SELECT r.car.id FROM Reservation r WHERE " +
           "r.startDate <= :endDate AND r.endDate >= :startDate)")
    List<Car> findAvailableCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate enDate);
}
