package pl.naukajazdy.naukajazdy.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driving_lesson")
public class DrivingLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private DrivingInstructor resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private LessonType category;

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
