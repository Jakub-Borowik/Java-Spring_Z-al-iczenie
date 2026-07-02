package pl.fotowoltaika.fotowoltaika.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pl.fotowoltaika.fotowoltaika.model.SolarServiceType;
import pl.fotowoltaika.fotowoltaika.model.SolarServiceOrder;
import pl.fotowoltaika.fotowoltaika.service.SolarServiceOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solar-services")
public class SolarServiceOrderController {
    private final SolarServiceOrderService service;

    @PostMapping
    public String create(@RequestParam SolarServiceType category,
            @RequestParam int requiredCapacity,
            @RequestParam int minQuality,
            @RequestParam int taskUnits,
            @RequestParam int priority,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        SolarServiceOrder record = service.createRecord(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        return "Created id: " + record.getId() + ", price: " + record.getTotalPrice()
                + ", score: " + record.getScore();
    }

    @GetMapping("/all")
    public List<SolarServiceOrder> all() {
        return service.getAllRecords();
    }
}
