package pl.pracownie.pracownie.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.pracownie.pracownie.model.LabType;
import pl.pracownie.pracownie.model.LabReservation;
import pl.pracownie.pracownie.service.LabReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school-labs")
public class LabReservationController {
    private final LabReservationService service;

    @PostMapping
    public String create(@RequestParam LabType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        LabReservation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<LabReservation> all() {
        return service.getAllRecords();
    }
}
