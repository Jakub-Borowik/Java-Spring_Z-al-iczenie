package pl.konkutery.helpdesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import pl.konkutery.helpdesk.service.TicketService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class ServiceTaskController {
    private final TicketService ticketService;

    
}
