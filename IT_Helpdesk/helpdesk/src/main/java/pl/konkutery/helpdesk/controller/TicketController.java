package pl.konkutery.helpdesk.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pl.konkutery.helpdesk.model.Ticket;
import pl.konkutery.helpdesk.model.TicketStatus;
import pl.konkutery.helpdesk.model.User;
import pl.konkutery.helpdesk.repository.TicketRepository;
import pl.konkutery.helpdesk.repository.UserRepository;
import pl.konkutery.helpdesk.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/helpdesk")
public class TicketController {
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    // Technician
    @GetMapping("/tickets")
    @PreAuthorize("hasAnyRole('TECHNICIAN')")
    public String showAllTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets";
    }

    // Technician
    @PostMapping("/{ticketId}/assign")
    @PreAuthorize("hasAnyRole('TECHNICIAN')")
    public String assignToMe(@PathVariable Long ticketId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser"); // Pierwsza metoda na
        ticketService.assignToMe(loggedUser.getId(), ticketId); // dowiedzenie sie kto aktualnei jest zalogowany
        return "redirect:/helpdesk/tickets";
    }

    // Technician
    @PostMapping("/{ticketId}/update")
    @PreAuthorize("hasAnyRole('TECHNICIAN')")
    public String updateTicketStatus(@PathVariable Long ticketId, @RequestParam TicketStatus status) {
        ticketService.updateStatus(ticketId, status);
        return "redirect:/helpdesk/tickets";
    }

    // Everyone
    @GetMapping("/{ticketId}/details")
    public String showTicketDetails(@PathVariable Long ticketId, Model model) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));
        model.addAttribute("ticket", ticket);
        model.addAttribute("totalCost", ticketService.calculateTotalCost(ticketId));
        model.addAttribute("totalRepairTime", ticketService.calculateRepairTime(ticketId));
        return "details";
    }

    // Client
    @GetMapping("/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "create";
    }

    // Client
    @PostMapping("/create")
    public String submitNewTicket(@ModelAttribute Ticket ticket) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName(); // Druga metoda!!!
        User client = userRepository.findByLogin(currentLogin)
                .orElseThrow(() -> new RuntimeException("User not found."));
            ticket.setClient(client);
            ticket.setTicketStatus(TicketStatus.NEW);
            ticketRepository.save(ticket);
        return "redirect:/helpdesk/tickets/my-tickets";
    }

}