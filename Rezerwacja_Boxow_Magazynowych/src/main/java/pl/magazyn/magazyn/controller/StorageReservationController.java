package pl.magazyn.magazyn.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.magazyn.magazyn.model.StorageType;
import pl.magazyn.magazyn.model.StorageReservation;
import pl.magazyn.magazyn.service.StorageReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageReservationController {
    private final StorageReservationService service;

    @PostMapping
    public String create(@RequestParam StorageType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        StorageReservation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<StorageReservation> all() {
        return service.getAllRecords();
    }
}
