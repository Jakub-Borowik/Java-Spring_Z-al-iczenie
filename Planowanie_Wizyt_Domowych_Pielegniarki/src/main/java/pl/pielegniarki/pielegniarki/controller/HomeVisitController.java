package pl.pielegniarki.pielegniarki.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.pielegniarki.pielegniarki.model.CareType;
import pl.pielegniarki.pielegniarki.model.HomeVisit;
import pl.pielegniarki.pielegniarki.service.HomeVisitService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home-visits")
public class HomeVisitController {
    private final HomeVisitService service;

    @PostMapping
    public String create(@RequestParam CareType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        HomeVisit record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<HomeVisit> all() {
        return service.getAllRecords();
    }
}
