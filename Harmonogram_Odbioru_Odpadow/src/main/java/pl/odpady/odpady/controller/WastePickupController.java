package pl.odpady.odpady.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.odpady.odpady.model.WasteType;
import pl.odpady.odpady.model.WastePickup;
import pl.odpady.odpady.service.WastePickupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waste-pickups")
public class WastePickupController {
    private final WastePickupService service;

    @PostMapping
    public String create(@RequestParam WasteType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        WastePickup record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<WastePickup> all() {
        return service.getAllRecords();
    }
}
