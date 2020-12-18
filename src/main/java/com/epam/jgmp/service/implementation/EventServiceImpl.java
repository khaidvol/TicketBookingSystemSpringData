package com.epam.jgmp.service.implementation;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.repository.EventRepository;
import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.service.EventService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

  private static final Log LOGGER = LogFactory.getLog(EventServiceImpl.class);
  private final EventRepository eventRepository;

  private EventServiceImpl(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event getEventById(long eventId) {

    Event event = eventRepository.findById(eventId);

    if (event == null) {
      LOGGER.error("Event not found.");
      throw new ApplicationException("Event not found", HttpStatus.NOT_FOUND);
    }

    LOGGER.info("Event found: " + event.toString());

    return event;
  }

  public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {

    List<Event> foundEvents = new ArrayList<>();

    for (Event event : eventRepository.findAll()) {
      if (event.getTitle().contains(title)) {
        foundEvents.add(event);
      }
    }

    LOGGER.info(String.format("%s event(s) found: ", foundEvents.size()));
    foundEvents.forEach(LOGGER::info);

    return foundEvents;
  }

  public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {

    List<Event> foundEvents = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(day);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    for (Event event : eventRepository.findAll()) {
      calendar.setTime(event.getDate());

      if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
        foundEvents.add(event);
      }
    }

    LOGGER.info(String.format("%s event(s) found: ", foundEvents.size()));
    foundEvents.forEach(LOGGER::info);

    return foundEvents;
  }

  public Event createEvent(Event event) {

    Event savedEvent = eventRepository.save(event);
    LOGGER.info("Event created successfully. Event details: " + savedEvent.toString());

    return eventRepository.findById(savedEvent.getId());
  }

  public Event updateEvent(Event event) {

    if (eventRepository.findById(event.getId()) == null) {
      LOGGER.error("Event not updated because of not found.");
      throw new ApplicationException("Event not updated", HttpStatus.NOT_FOUND);
    }

    eventRepository.save(event);
    LOGGER.info("Event updated successfully. Event details: " + event.toString());

    return eventRepository.findById(event.getId());
  }

  public boolean deleteEvent(long eventId) {

    boolean isEventDeleted = false;

    if (eventRepository.findById(eventId) != null) {
      eventRepository.deleteById(eventId);
      isEventDeleted = true;
    }
    LOGGER.info("Event deleted: " + isEventDeleted);

    return isEventDeleted;
  }
}
