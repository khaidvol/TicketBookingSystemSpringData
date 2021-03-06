package com.epam.jgmp.facade;

import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.repository.model.Ticket;
import com.epam.jgmp.repository.model.User;
import com.epam.jgmp.repository.model.UserAccount;
import com.epam.jgmp.service.EventService;
import com.epam.jgmp.service.TicketService;
import com.epam.jgmp.service.UserAccountService;
import com.epam.jgmp.service.UserService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade {

  private final UserService userService;
  private final EventService eventService;
  private final TicketService ticketService;
  private final UserAccountService accountService;

  // constructor-injection
  private BookingFacadeImpl(
      UserService userService,
      EventService eventService,
      TicketService ticketService,
      UserAccountService accountService) {
    this.userService = userService;
    this.eventService = eventService;
    this.ticketService = ticketService;
    this.accountService = accountService;
  }

  @Override
  public Event getEventById(long eventId) {
    return eventService.getEventById(eventId);
  }

  @Override
  public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
    return eventService.getEventsByTitle(title, pageSize, pageNum);
  }

  @Override
  public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
    return eventService.getEventsForDay(day, pageSize, pageNum);
  }

  @Override
  public Event createEvent(Event event) {
    return eventService.createEvent(event);
  }

  @Override
  public Event updateEvent(Event event) {
    return eventService.updateEvent(event);
  }

  @Override
  public boolean deleteEvent(long eventId) {
    return eventService.deleteEvent(eventId);
  }

  @Override
  public User getUserById(long userId) {
    return userService.getUserById(userId);
  }

  @Override
  public User getUserByEmail(String email) {
    return userService.getUserByEmail(email);
  }

  @Override
  public List<User> getUsersByName(String name, int pageSize, int pageNum) {
    return userService.getUsersByName(name, pageSize, pageNum);
  }

  @Override
  public User createUser(User user) {
    return userService.createUser(user);
  }

  @Override
  public User updateUser(User user) {
    return userService.updateUser(user);
  }

  @Override
  public boolean deleteUser(long userId) {
    return userService.deleteUser(userId);
  }

  @Override
  public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
    return ticketService.bookTicket(userId, eventId, place, category);
  }

  @Override
  public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
    return ticketService.getBookedTickets(user, pageSize, pageNum);
  }

  @Override
  public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
    return ticketService.getBookedTickets(event, pageSize, pageNum);
  }

  @Override
  public boolean cancelTicket(long ticketId) {
    return ticketService.cancelTicket(ticketId);
  }

  @Override
  public List<Ticket> preloadTicketsFromFile(FileInputStream fileInputStream) {
    return ticketService.preloadTicketsFromFile(fileInputStream);
  }

  @Override
  public UserAccount getUserAccountById(long accountId) {
    return accountService.getUserAccountById(accountId);
  }

  @Override
  public UserAccount getUserAccountByUserId(long userId) {
    return accountService.getUserAccountByUserId(userId);
  }

  @Override
  public UserAccount createUserAccount(UserAccount userAccount) {
    return accountService.createUserAccount(userAccount);
  }

  @Override
  public boolean deleteUserAccount(long accountId) {
    return accountService.deleteUserAccount(accountId);
  }

  @Override
  public boolean refillUserAccount(long accountId, double amount) {
    return accountService.refillUserAccount(accountId, amount);
  }

  @Override
  public boolean withdrawFromUserAccount(long accountId, double ticketPrice) {
    return accountService.withdrawFromUserAccount(accountId, ticketPrice);
  }
}
