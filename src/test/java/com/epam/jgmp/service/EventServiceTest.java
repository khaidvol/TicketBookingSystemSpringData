package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventServiceTest {

  public static final String TEST_EVENT = "Test Event";
  public static final String TEST_EVENT_2 = "Test Event_2";
  public static final double TICKET_PRICE = 25.50;

  @Autowired private EventService eventService;
  private Event event;
  private List<Event> events;

  @BeforeEach
  public void setUp() throws ParseException {

    String inputString = "2020-06-28";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date eventDate = dateFormat.parse(inputString);

    event = eventService.createEvent(new Event(TEST_EVENT, eventDate, TICKET_PRICE));
    events = Collections.singletonList(event);
  }

  @Test
  void getEventByIdTest() {
    Assertions.assertEquals(event, eventService.getEventById(event.getId()));
  }

  @Test
  void getNotExistingEventByIdTest() {
    Assertions.assertThrows(ApplicationException.class, () -> eventService.getEventById(1000L));
  }

  @Test
  void getEventsByTitleTest() {
    Assertions.assertEquals(events, eventService.getEventsByTitle(event.getTitle(), 1, 1));
  }

  @Test
  void getEventsForDayTest() throws ParseException {
    Assertions.assertEquals(events, eventService.getEventsForDay(event.getDate(), 1, 1));
  }

  @Test
  void createEventTest() {
    Assertions.assertEquals(event, eventService.createEvent(event));
  }

  @Test
  void updateEventTest() {
    event.setTitle(TEST_EVENT_2);

    Assertions.assertEquals(event, eventService.updateEvent(event));
  }

  @Test
  void updateNotExistingEventTest() {
    event.setId(100L);

    Assertions.assertThrows(ApplicationException.class, () -> eventService.updateEvent(event));
  }

  @Test
  void deleteEventTest() {
    Assertions.assertTrue(eventService.deleteEvent(event.getId()));
  }
}
