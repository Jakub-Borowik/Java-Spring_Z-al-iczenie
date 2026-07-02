package pl.coworking.coworking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coworking_booking")
public class CoworkingBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private WorkDesk resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private DeskType category;

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
