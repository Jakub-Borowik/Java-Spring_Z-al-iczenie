package pl.naukajazdy.naukajazdy.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.naukajazdy.naukajazdy.model.LessonType;
import pl.naukajazdy.naukajazdy.model.DrivingLesson;
import pl.naukajazdy.naukajazdy.service.DrivingLessonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/driving-lessons")
public class DrivingLessonController {
    private final DrivingLessonService service;

    @PostMapping
    public String create(@RequestParam LessonType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        DrivingLesson record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<DrivingLesson> all() {
        return service.getAllRecords();
    }
}
