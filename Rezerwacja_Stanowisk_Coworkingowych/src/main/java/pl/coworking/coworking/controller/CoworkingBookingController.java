package pl.coworking.coworking.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.coworking.coworking.model.DeskType;
import pl.coworking.coworking.model.CoworkingBooking;
import pl.coworking.coworking.service.CoworkingBookingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coworking-bookings")
public class CoworkingBookingController {
    private final CoworkingBookingService service;

    @PostMapping
    public String create(@RequestParam DeskType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        CoworkingBooking record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<CoworkingBooking> all() {
        return service.getAllRecords();
    }
}
