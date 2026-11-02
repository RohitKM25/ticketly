package io.github.rohitkm25.ticketly.repository;

import io.github.rohitkm25.ticketly.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByShowIdAndSeatNumber(Long showId, String seatNumber);
}
