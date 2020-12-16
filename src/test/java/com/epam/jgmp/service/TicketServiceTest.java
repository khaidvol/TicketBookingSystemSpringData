package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import com.epam.jgmp.model.UserAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketServiceTest {

  @Autowired private TicketService ticketService;
  @Autowired private EventService eventService;
  @Autowired private UserAccountService userAccountService;

  private Ticket ticket;

  @BeforeEach
  public void setUp() {

    eventService.createEvent(new Event("Test Event", new Date(), 25));
    userAccountService.createUserAccount(new UserAccount(1L, 1000));
    ticket = ticketService.bookTicket(1L, 1L, 1, Ticket.Category.STANDARD);
  }

  @Test
  void bookTicketTest() {
    Assertions.assertNotNull(
        ticketService.bookTicket(ticket.getUserId(), ticket.getEventId(), 2, ticket.getCategory()));
  }

  @Test
  void bookTicketForAlreadyBookedPlaceTest() {
    Assertions.assertThrows(
        ApplicationException.class,
        () -> ticketService.bookTicket(1, 1, 1, Ticket.Category.STANDARD));
  }

  @Test
  void getTicketsByUserTest() {

    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(1L);

    Assertions.assertEquals(1, ticketService.getBookedTickets(user, 1, 1).size());
  }

  @Test
  void getTicketsByEventTest() {

    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(1L);

    Assertions.assertEquals(1, ticketService.getBookedTickets(event, 1, 1).size());
  }

  @Test
  void cancelTicketTest() {
    Assertions.assertTrue(ticketService.cancelTicket(1));
  }
}
