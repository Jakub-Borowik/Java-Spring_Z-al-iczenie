package pl.terapia.terapia.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.terapia.terapia.model.TherapyType;
import pl.terapia.terapia.model.TherapySession;
import pl.terapia.terapia.service.TherapySessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/therapy")
public class TherapySessionController {
    private final TherapySessionService service;

    @PostMapping
    public String create(@RequestParam TherapyType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        TherapySession record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<TherapySession> all() {
        return service.getAllRecords();
    }
}
