package pl.okna.okna.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.okna.okna.model.WindowServiceType;
import pl.okna.okna.model.WindowCleaningOrder;
import pl.okna.okna.service.WindowCleaningOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/window-cleaning")
public class WindowCleaningOrderController {
    private final WindowCleaningOrderService service;

    @PostMapping
    public String create(@RequestParam WindowServiceType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        WindowCleaningOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<WindowCleaningOrder> all() {
        return service.getAllRecords();
    }
}
