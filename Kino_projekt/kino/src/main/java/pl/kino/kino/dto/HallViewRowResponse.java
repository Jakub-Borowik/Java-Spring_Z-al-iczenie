package pl.kino.kino.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallViewRowResponse {
    private int rowNumber;
    private List<HallViewSeatResponse> seats;
}
