package pl.kino.kino.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import pl.kino.kino.model.TicketType;

@Service
public class TicketPriceService {
    public BigDecimal calculatePrice(TicketType ticketType) {
        return switch (ticketType) {
            case NORMAL -> new BigDecimal("30.00");
            case STUDENT -> new BigDecimal("22.00");
            case CHILD -> new BigDecimal("18.00");
            case SENIOR -> new BigDecimal("20.00");
        };
    }
}
