package pl.odsniezanie.odsniezanie.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.odsniezanie.odsniezanie.model.SnowZone;
import pl.odsniezanie.odsniezanie.model.SnowRemovalOrder;
import pl.odsniezanie.odsniezanie.service.SnowRemovalOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snow-removal")
public class SnowRemovalOrderController {
    private final SnowRemovalOrderService service;

    @PostMapping
    public String create(@RequestParam SnowZone category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        SnowRemovalOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<SnowRemovalOrder> all() {
        return service.getAllRecords();
    }
}
