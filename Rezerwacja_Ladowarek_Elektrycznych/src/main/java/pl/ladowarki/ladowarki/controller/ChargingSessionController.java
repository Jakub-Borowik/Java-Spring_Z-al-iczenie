package pl.ladowarki.ladowarki.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.ladowarki.ladowarki.model.ChargingType;
import pl.ladowarki.ladowarki.model.ChargingSession;
import pl.ladowarki.ladowarki.service.ChargingSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charging-sessions")
public class ChargingSessionController {
    private final ChargingSessionService service;

    @PostMapping
    public String create(@RequestParam ChargingType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        ChargingSession record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<ChargingSession> all() {
        return service.getAllRecords();
    }
}
