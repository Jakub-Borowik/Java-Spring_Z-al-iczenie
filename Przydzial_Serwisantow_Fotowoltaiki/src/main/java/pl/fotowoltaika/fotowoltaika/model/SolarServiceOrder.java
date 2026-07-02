package pl.fotowoltaika.fotowoltaika.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solar_service_order")
public class SolarServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private SolarTechnician resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private SolarServiceType category;

    private int requiredCapacity;

    private int minQuality;

    private int taskUnits;

    private int priority;

    private int calculatedHours;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal score;

    private BigDecimal totalPrice;
}
