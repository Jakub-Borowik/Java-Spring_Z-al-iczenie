package pl.kino.kino.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kino.kino.model.TicketType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {
    private List<TicketType> ticketTypes;
}
