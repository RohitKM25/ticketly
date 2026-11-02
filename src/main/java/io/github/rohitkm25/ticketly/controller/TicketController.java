package io.github.rohitkm25.ticketly.controller;

import io.github.rohitkm25.ticketly.model.Show;
import io.github.rohitkm25.ticketly.model.Ticket;
import io.github.rohitkm25.ticketly.repository.ShowRepository;
import io.github.rohitkm25.ticketly.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TicketController {

    private final TicketService ticketService;
    private final ShowRepository showRepository;

    public TicketController(TicketService ticketService, ShowRepository showRepository) {
        this.ticketService = ticketService;
        this.showRepository = showRepository;
    }

    @GetMapping({"", "/shows"})
    public String listShows(Model model) {
        model.addAttribute("shows", showRepository.findAll());
        return "shows";
    }

    @GetMapping("/shows/{id}/book")
    public String showBookingPage(@PathVariable Long id, Model model) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid show ID: " + id));
        model.addAttribute("show", show);
        return "book";
    }

    @PostMapping("/tickets/book")
    public String bookTicket(@RequestParam Long showId,
                             @RequestParam String seatNumber,
                             @RequestParam String userId,
                             Model model) {
        try {
            Ticket ticket = ticketService.bookTicket(showId, seatNumber, userId);
            model.addAttribute("ticket", ticket);
            return "success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            Show show = showRepository.findById(showId).orElse(null);
            model.addAttribute("show", show);
            return "book";
        }
    }
}
