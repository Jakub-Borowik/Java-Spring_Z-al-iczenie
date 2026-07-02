package pl.egzaminy.egzaminy.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.egzaminy.egzaminy.model.ExamType;
import pl.egzaminy.egzaminy.model.ExamReservation;
import pl.egzaminy.egzaminy.service.ExamReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exam-rooms")
public class ExamReservationController {
    private final ExamReservationService service;

    @PostMapping
    public String create(@RequestParam ExamType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        ExamReservation record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<ExamReservation> all() {
        return service.getAllRecords();
    }
}
