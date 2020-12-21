package com.epam.jgmp.repository;

import com.epam.jgmp.repository.model.Ticket;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

  Ticket save(Ticket ticket);

  @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
  Ticket findById(long id);

  List<Ticket> findAll();

  Ticket deleteById(long id);
}
