package com.epam.jgmp.repository;

import com.epam.jgmp.repository.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

  Ticket save(Ticket ticket);

  Ticket findById(long id);

  List<Ticket> findAll();

  Ticket deleteById(long id);
}
