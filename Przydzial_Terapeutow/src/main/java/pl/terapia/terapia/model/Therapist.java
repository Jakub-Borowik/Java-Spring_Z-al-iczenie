package pl.terapia.terapia.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "therapist")
public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TherapyType category;

    private int capacity;

    private int quality;

    private int unitsPerHour;

    private BigDecimal pricePerHour;

    private boolean available;
}
