package pl.egzaminy.egzaminy.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam_room")
public class ExamRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ExamType category;

    private int capacity;

    private int quality;

    private int unitsPerHour;

    private BigDecimal pricePerHour;

    private boolean available;
}
