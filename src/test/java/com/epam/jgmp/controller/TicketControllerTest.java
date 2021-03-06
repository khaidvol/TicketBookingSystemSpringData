package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.repository.model.Ticket;
import com.epam.jgmp.repository.model.User;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private BookingFacade bookingFacade;

  private Ticket ticket;
  private List<Ticket> tickets;

  private ObjectMapper objectMapper;
  private ObjectNode objectNode;

  @BeforeEach
  public void setUp() throws ParseException {

    objectMapper = new ObjectMapper();
    objectNode = objectMapper.createObjectNode();

    long ticketId = 1L;
    long userId = 1L;
    long eventId = 1L;
    int place = 1;
    Ticket.Category category = Ticket.Category.STANDARD;

    ticket = new Ticket(userId, eventId, place, category);
    ticket.setId(ticketId);
    tickets = Collections.singletonList(ticket);

    Mockito.when(bookingFacade.bookTicket(userId, eventId, place, category)).thenReturn(ticket);
    Mockito.when(
            bookingFacade.getBookedTickets(
                ArgumentMatchers.any(User.class),
                ArgumentMatchers.any(Integer.class),
                ArgumentMatchers.any(Integer.class)))
        .thenReturn(tickets);
    Mockito.when(
            bookingFacade.getBookedTickets(
                ArgumentMatchers.any(Event.class),
                ArgumentMatchers.any(Integer.class),
                ArgumentMatchers.any(Integer.class)))
        .thenReturn(tickets);
    Mockito.when(bookingFacade.cancelTicket(ticketId)).thenReturn(true);
  }

  @Test
  void bookTicket() throws Exception {

    objectNode.put("userId", ticket.getUserId());
    objectNode.put("eventId", ticket.getEventId());
    objectNode.put("place", ticket.getPlace());
    objectNode.put("category", ticket.getCategory().toString());

    this.mockMvc
        .perform(
            post("/ticket/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void getBookedTicketsForUser() throws Exception {
    long id = 1L;
    String name = "Test";
    String email = "test@gmail.com";

    this.mockMvc
        .perform(
            get(
                "/ticket/userTickets?id={id}&name={name}&email={email}&pageSize=1&pageNum=1",
                id,
                name,
                email))
        .andExpect(status().isOk())
        .andExpect(view().name("ticketTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", tickets));
  }

  @Test
  void getBookedTicketsForEvent() throws Exception {

    long id = 1L;
    String title = "Test";
    String day = "2020-12-12";
    double price = 25;

    Mockito.when(
            bookingFacade.getBookedTickets(
                ArgumentMatchers.any(Event.class),
                ArgumentMatchers.any(Integer.class),
                ArgumentMatchers.any(Integer.class)))
        .thenReturn(tickets);

    this.mockMvc
        .perform(
            get(
                "/ticket/eventTickets?id={id}&title={title}&day={day}&price={price}&pageSize=1&pageNum=1",
                id,
                title,
                day,
                price))
        .andExpect(status().isOk())
        .andExpect(view().name("ticketTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", tickets));
  }

  @Test
  void cancelTicket() throws Exception {
    this.mockMvc
            .perform(delete("/ticket/cancel/{id}", ticket.getId()))
            .andExpect(status().isOk())
            .andReturn();
  }
}
