package com.epam.jgmp.integration;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.repository.model.Ticket;
import com.epam.jgmp.repository.model.User;
import com.epam.jgmp.repository.model.UserAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.epam.jgmp.repository.model.Ticket.Category.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {

  @Autowired BookingFacade bookingFacade;

  @Test
  void testIntegrationScenario() {
    User userAlfred = bookingFacade.createUser(new User("Alfred", "alfred@gmail.com"));
    User userRobert = bookingFacade.createUser(new User("Robert", "robert@gmail.com"));
    User userWilliam = bookingFacade.createUser(new User("William", "william@gmail.com"));

    UserAccount alfredUserAccount =
        bookingFacade.createUserAccount(new UserAccount(userAlfred.getId(), 100));
    UserAccount robertUserAccount =
        bookingFacade.createUserAccount(new UserAccount(userRobert.getId(), 100));
    UserAccount williamUserAccount =
        bookingFacade.createUserAccount(new UserAccount(userWilliam.getId(), 100));

    Event event1 = bookingFacade.createEvent(new Event("Dancing Show", new Date(), 25));

    Ticket ticket1 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 1, STANDARD);
    Ticket ticket2 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 2, PREMIUM);
    Ticket ticket3 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 3, BAR);

    Ticket ticket4 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 4, STANDARD);
    Ticket ticket5 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 5, PREMIUM);
    Ticket ticket6 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 6, BAR);

    Ticket ticket7 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 7, STANDARD);
    Ticket ticket8 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 8, PREMIUM);
    Ticket ticket9 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 9, BAR);

    List<Ticket> expectedTicketsByUser = Arrays.asList(ticket1, ticket2, ticket3);
    List<Ticket> actualTicketsByUser = bookingFacade.getBookedTickets(userAlfred, 1, 1);

    List<Ticket> expectedTicketsByEvent =
        Arrays.asList(
            ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8, ticket9);
    List<Ticket> actualTicketsByEvent = bookingFacade.getBookedTickets(event1, 1, 1);

    Assertions.assertEquals(expectedTicketsByUser, actualTicketsByUser);
    Assertions.assertEquals(expectedTicketsByEvent, actualTicketsByEvent);
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket3.getId()));
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket2.getId()));
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket1.getId()));
  }
}
