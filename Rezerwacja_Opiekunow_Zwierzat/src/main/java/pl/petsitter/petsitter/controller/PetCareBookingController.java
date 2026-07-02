package pl.petsitter.petsitter.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.petsitter.petsitter.model.PetCareType;
import pl.petsitter.petsitter.model.PetCareBooking;
import pl.petsitter.petsitter.service.PetCareBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet-care")
public class PetCareBookingController {
    private final PetCareBookingService service;

    @PostMapping
    public String create(@RequestParam PetCareType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        PetCareBooking record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<PetCareBooking> all() {
        return service.getAllRecords();
    }
}
