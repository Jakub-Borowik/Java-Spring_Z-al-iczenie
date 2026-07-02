package pl.klimatyzacja.klimatyzacja.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.klimatyzacja.klimatyzacja.model.AcServiceType;
import pl.klimatyzacja.klimatyzacja.model.AcServiceOrder;
import pl.klimatyzacja.klimatyzacja.service.AcServiceOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ac-services")
public class AcServiceOrderController {
    private final AcServiceOrderService service;

    @PostMapping
    public String create(@RequestParam AcServiceType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        AcServiceOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<AcServiceOrder> all() {
        return service.getAllRecords();
    }
}
