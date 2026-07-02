package pl.diety.diety.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.diety.diety.model.DietArea;
import pl.diety.diety.model.MealDelivery;
import pl.diety.diety.service.MealDeliveryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal-deliveries")
public class MealDeliveryController {
    private final MealDeliveryService service;

    @PostMapping
    public String create(@RequestParam DietArea category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        MealDelivery record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<MealDelivery> all() {
        return service.getAllRecords();
    }
}
