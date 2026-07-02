package pl.internet.internet.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.internet.internet.model.InstallationType;
import pl.internet.internet.model.InternetInstallation;
import pl.internet.internet.service.InternetInstallationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internet-installations")
public class InternetInstallationController {
    private final InternetInstallationService service;

    @PostMapping
    public String create(@RequestParam InstallationType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        InternetInstallation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<InternetInstallation> all() {
        return service.getAllRecords();
    }
}
