package pl.coworking.coworking.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_desk")
public class WorkDesk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DeskType category;

    private int capacity;

    private int quality;

    private int unitsPerHour;

    private BigDecimal pricePerHour;

    private boolean available;
}
