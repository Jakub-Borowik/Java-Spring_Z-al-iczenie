package pl.remonty.remonty.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.remonty.remonty.model.RenovationType;
import pl.remonty.remonty.model.RenovationOrder;
import pl.remonty.remonty.service.RenovationOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/renovations")
public class RenovationOrderController {
    private final RenovationOrderService service;

    @PostMapping
    public String create(@RequestParam RenovationType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        RenovationOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<RenovationOrder> all() {
        return service.getAllRecords();
    }
}
