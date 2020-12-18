package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private BookingFacade bookingFacade;

  private Event event;
  List<Event> events;

  private ObjectMapper objectMapper;
  private ObjectNode objectNode;

  @BeforeEach
  public void setUp() throws ParseException {

    objectMapper = new ObjectMapper();
    objectNode = objectMapper.createObjectNode();

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
    this.mockMvc
            .perform(get("/event/id?id={id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("eventTemplate"))
            .andExpect(model().attributeExists("result"))
            .andExpect(model().attribute("result", event));
  }

  @Test
  void getEventsByTitle() throws Exception {
    this.mockMvc
            .perform(get("/event/title?title={title}&pageSize=1&pageNum=1", event.getTitle()))
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

    objectNode.put("title", event.getTitle());
    objectNode.put("day", event.getDate().toString());
    objectNode.put("ticketPrice", event.getTicketPrice());

    this.mockMvc
            .perform(
                    post("/event/new")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectNode.toString()))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  void updateEvent() throws Exception {

    objectNode.put("id", event.getId());
    objectNode.put("title", "Test1Event");
    objectNode.put("day", event.getDate().toString());
    objectNode.put("ticketPrice", event.getTicketPrice());

    this.mockMvc
            .perform(
                    put("/event/update/{id}", event.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectNode.toString()))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  void deleteEvent() throws Exception {
    this.mockMvc
            .perform(delete("/event/delete/{id}", event.getId()))
            .andExpect(status().isOk())
            .andReturn();
  }
}
