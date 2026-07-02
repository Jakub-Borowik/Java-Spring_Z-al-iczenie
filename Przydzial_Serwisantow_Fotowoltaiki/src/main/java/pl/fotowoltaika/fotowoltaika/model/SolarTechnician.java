package pl.fotowoltaika.fotowoltaika.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solar_technician")
public class SolarTechnician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SolarServiceType category;

    private int capacity;

    private int quality;

    private int unitsPerHour;

    private BigDecimal pricePerHour;

    private boolean available;
}
