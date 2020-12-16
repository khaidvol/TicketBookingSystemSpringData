package com.epam.jgmp.integration;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import com.epam.jgmp.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
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

  @BeforeEach
  public void setUp() throws ParseException {

    user = bookingFacade.createUser(new User("Jack", "jack@gmail.com"));
    userAccount = bookingFacade.createUserAccount(new UserAccount(user.getId(), 2000));
    event = bookingFacade.createEvent(new Event("Event1", new Date(), 30));
    ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD);
    tickets = Collections.singletonList(ticket);
  }

  @Test
  void bookTicket() throws Exception {
    bookingFacade.cancelTicket(ticket.getId());
    long userId = 1L;
    long eventId = 1L;
    int place = 666;
    Ticket.Category category = Ticket.Category.STANDARD;

    this.mockMvc
        .perform(
            post(
                "/ticket/book?userId={userId}&eventId={eventId}&place={place}&category={category}",
                userId,
                eventId,
                place,
                category))
        .andExpect(status().isOk())
        .andExpect(view().name("ticketTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model()
                .attribute(
                    "result",
                    bookingFacade
                        .getBookedTickets(bookingFacade.getUserById(userId), 1, 1)
                        .get(0)));
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

    long id = ticket.getId();

    this.mockMvc
        .perform(delete("/ticket/cancel?id={id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("ticketTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model()
                .attribute("result", hasToString(String.format("Ticket #%s canceled: true", id))));
  }
}
