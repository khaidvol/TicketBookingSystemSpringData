package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private BookingFacade bookingFacade;

  private Event event;
  List<Event> events;

  @BeforeEach
  public void setUp() throws ParseException {

    long id = 1L;
    double price = 25;

    String title = "TestEvent";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateInString = "2020-12-12";
    Date date = formatter.parse(dateInString);

    event = new Event(title, date, price);
    event.setId(id);
    events = Collections.singletonList(event);

    Mockito.when(bookingFacade.getEventById(id)).thenReturn(event);
    Mockito.when(
            bookingFacade.getEventsForDay(
                ArgumentMatchers.any(Date.class),
                ArgumentMatchers.any(Integer.class),
                ArgumentMatchers.any(Integer.class)))
        .thenReturn(events);

    Mockito.when(bookingFacade.getEventsByTitle(title, 1, 1)).thenReturn(events);
    Mockito.when(bookingFacade.createEvent(ArgumentMatchers.any(Event.class))).thenReturn(event);
    Mockito.when(bookingFacade.updateEvent(ArgumentMatchers.any(Event.class))).thenReturn(event);
    Mockito.when(bookingFacade.deleteEvent(id)).thenReturn(true);
  }

  @Test
  void getEventById() throws Exception {

    long id = 1L;

    this.mockMvc
        .perform(get("/event/id?id={id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", event));
  }

  @Test
  void getEventsByTitle() throws Exception {

    String title = "TestEvent";

    this.mockMvc
        .perform(get("/event/title?title={title}&pageSize=1&pageNum=1", title))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", events));
  }

  @Test
  void getEventsForDay() throws Exception {

    String day = "2020-12-12";

    this.mockMvc
        .perform(get("/event/day?day={day}&pageSize=1&pageNum=1", day))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", events));
  }

  @Test
  void createEvent() throws Exception {

    String title = "TestEvent";
    String day = "2020-12-12";
    double price = 25;

    this.mockMvc
        .perform(post("/event/new?title={title}&day={day}&price={price}", title, day, price))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model().attribute("result", hasToString("Event created: ".concat(event.toString()))));
  }

  @Test
  void updateEvent() throws Exception {

    long id = 1L;
    String title = "TestEvent";
    String day = "2020-12-12";
    double price = 25;

    this.mockMvc
        .perform(
            put(
                "/event/update?id={id}&title={title}&day={day}&price={price}",
                id,
                title,
                day,
                price))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model().attribute("result", hasToString("Event updated: ".concat(event.toString()))));
  }

  @Test
  void deleteEvent() throws Exception {

    long id = 1L;

    this.mockMvc
        .perform(delete(String.format("/event/delete?id=%s", id)))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model().attribute("result", hasToString(String.format("Event #%s deleted: true", id))));
  }
}
