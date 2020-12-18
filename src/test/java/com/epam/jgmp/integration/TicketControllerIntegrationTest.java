package com.epam.jgmp.integration;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.repository.model.Ticket;
import com.epam.jgmp.repository.model.User;
import com.epam.jgmp.repository.model.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private BookingFacade bookingFacade;

  private User user;
  private UserAccount userAccount;
  private Event event;
  private Ticket ticket;
  private List<Ticket> tickets;

  private ObjectMapper objectMapper;
  private ObjectNode objectNode;

  @BeforeEach
  public void setUp() throws ParseException {

    objectMapper = new ObjectMapper();
    objectNode = objectMapper.createObjectNode();

    user = bookingFacade.createUser(new User("Jack", "jack@gmail.com"));
    userAccount = bookingFacade.createUserAccount(new UserAccount(user.getId(), 2000));
    event = bookingFacade.createEvent(new Event("Event1", new Date(), 30));
    ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD);
    tickets = Collections.singletonList(ticket);
  }

  @Test
  void bookTicket() throws Exception {
    bookingFacade.cancelTicket(ticket.getId());

    objectNode.put("userId", user.getId());
    objectNode.put("eventId", event.getId());
    objectNode.put("place", 666);
    objectNode.put("category", Ticket.Category.STANDARD.toString());

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

    long id = user.getId();
    String name = user.getName();
    String email = user.getEmail();

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
        .andExpect(model().attribute("result", bookingFacade.getBookedTickets(user, 1, 1)));
  }

  @Test
  void getBookedTicketsForEvent() throws Exception {

    long id = event.getId();
    String title = event.getTitle();
    String day = event.getDate().toString();
    double price = event.getTicketPrice();

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
        .andExpect(model().attribute("result", bookingFacade.getBookedTickets(event, 1, 1)));
  }

  @Test
  void cancelTicket() throws Exception {

    this.mockMvc
        .perform(delete("/ticket/cancel/{id}", ticket.getId()))
        .andExpect(status().isOk())
        .andReturn();
  }
}
