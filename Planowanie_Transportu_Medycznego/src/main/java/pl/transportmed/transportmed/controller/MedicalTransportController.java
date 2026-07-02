package pl.transportmed.transportmed.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.transportmed.transportmed.model.TransportType;
import pl.transportmed.transportmed.model.MedicalTransport;
import pl.transportmed.transportmed.service.MedicalTransportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical-transport")
public class MedicalTransportController {
    private final MedicalTransportService service;

    @PostMapping
    public String create(@RequestParam TransportType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        MedicalTransport record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<MedicalTransport> all() {
        return service.getAllRecords();
    }
}
