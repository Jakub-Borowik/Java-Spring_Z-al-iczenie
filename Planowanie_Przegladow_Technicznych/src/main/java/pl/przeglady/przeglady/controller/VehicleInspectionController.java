package pl.przeglady.przeglady.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.przeglady.przeglady.model.InspectionType;
import pl.przeglady.przeglady.model.VehicleInspection;
import pl.przeglady.przeglady.service.VehicleInspectionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inspections")
public class VehicleInspectionController {
    private final VehicleInspectionService service;

    @PostMapping
    public String create(@RequestParam InspectionType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        VehicleInspection record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<VehicleInspection> all() {
        return service.getAllRecords();
    }
}
