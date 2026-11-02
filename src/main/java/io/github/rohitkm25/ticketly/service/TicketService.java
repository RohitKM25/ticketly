package io.github.rohitkm25.ticketly.service;

import io.github.rohitkm25.ticketly.model.Show;
import io.github.rohitkm25.ticketly.model.Ticket;
import io.github.rohitkm25.ticketly.repository.ShowRepository;
import io.github.rohitkm25.ticketly.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    public TicketService(ShowRepository showRepository, TicketRepository ticketRepository) {
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket bookTicket(Long showId, String seatNumber, String userId) {
        // 1. Check database for existing booking on this show and seat
        if (ticketRepository.existsByShowIdAndSeatNumber(showId, seatNumber)) {
            throw new IllegalStateException("Seat " + seatNumber + " is already booked.");
        }

        // 2. Fetch show reference
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found with ID: " + showId));

        // 3. Create and save ticket (database unique constraint provides final safety net)
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeatNumber(seatNumber);
        ticket.setUserId(userId);

        return ticketRepository.save(ticket);
    }
}
