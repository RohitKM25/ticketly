package io.github.rohitkm25.ticketly.repository;

import io.github.rohitkm25.ticketly.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
}
