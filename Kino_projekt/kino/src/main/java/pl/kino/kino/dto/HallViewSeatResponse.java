package pl.kino.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallViewSeatResponse {
    private Long seatId;
    private int seatNumber;
    private boolean occupied;
}
