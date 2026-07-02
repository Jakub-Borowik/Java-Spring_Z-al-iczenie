package pl.naukajazdy.naukajazdy.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driving_instructor")
public class DrivingInstructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private LessonType category;

    private int capacity;

    private int quality;

    private int unitsPerHour;

    private BigDecimal pricePerHour;

    private boolean available;
}
