package com.example.garage.repository;

import com.example.garage.model.entity.Ticket;
import com.example.garage.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-23
 */

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findAllByStatus(Status status);
}
