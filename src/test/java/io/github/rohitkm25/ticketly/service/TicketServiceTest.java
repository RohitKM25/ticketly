package io.github.rohitkm25.ticketly.service;

import io.github.rohitkm25.ticketly.model.Show;
import io.github.rohitkm25.ticketly.model.Ticket;
import io.github.rohitkm25.ticketly.repository.ShowRepository;
import io.github.rohitkm25.ticketly.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void bookTicket_savesTicketWhenSeatIsAvailable() {
        Show show = new Show();
        show.setId(1L);
        show.setMovieTitle("Inception");

        when(ticketRepository.existsByShowIdAndSeatNumber(1L, "A1")).thenReturn(false);
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(call -> call.getArgument(0));

        Ticket ticket = ticketService.bookTicket(1L, "A1", "user@example.com");

        assertThat(ticket.getShow()).isSameAs(show);
        assertThat(ticket.getSeatNumber()).isEqualTo("A1");
        assertThat(ticket.getUserId()).isEqualTo("user@example.com");
    }

    @Test
    void bookTicket_throwsWhenSeatIsAlreadyBooked() {
        when(ticketRepository.existsByShowIdAndSeatNumber(1L, "A1")).thenReturn(true);

        assertThatThrownBy(() -> ticketService.bookTicket(1L, "A1", "user@example.com"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void bookTicket_throwsWhenShowDoesNotExist() {
        when(ticketRepository.existsByShowIdAndSeatNumber(99L, "A1")).thenReturn(false);
        when(showRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.bookTicket(99L, "A1", "user@example.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
