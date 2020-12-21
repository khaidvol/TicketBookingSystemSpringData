package com.epam.jgmp.repository;

import com.epam.jgmp.repository.model.Event;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

  Event save(Event event);

  @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
  Event findById(long id);

  List<Event> findAll();

  Event deleteById(long id);
}
