package pl.magazyn.magazyn.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage_reservation")
public class StorageReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private StorageBox resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private StorageType category;

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
