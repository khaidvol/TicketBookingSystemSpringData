package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import com.epam.jgmp.model.UserAccount;
import com.epam.jgmp.repository.EventRepository;
import com.epam.jgmp.repository.TicketRepository;
import com.epam.jgmp.repository.UserRepository;
import com.epam.jgmp.xml.ObjXMLMapper;
import com.epam.jgmp.xml.XMLTicket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TicketService {

  private static final Log LOGGER = LogFactory.getLog(TicketService.class);
  private final TicketRepository ticketRepository;
  private final UserRepository userRepository;
  private final EventRepository eventRepository;
  private final ObjXMLMapper objXMLMapper;
  private final UserAccountService userAccountService;
  private final EventService eventService;

  private TicketService(
      TicketRepository ticketRepository,
      UserRepository userRepository,
      EventRepository eventRepository,
      EventService eventService,
      UserAccountService userAccountService,
      ObjXMLMapper objXMLMapper) {
    this.ticketRepository = ticketRepository;
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
    this.objXMLMapper = objXMLMapper;
    this.userAccountService = userAccountService;
    this.eventService = eventService;
  }

  public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {

    Ticket ticket = new Ticket(userId, eventId, place, category);

    if (!isPlaceFree(ticket)) {
      LOGGER.error(
          String.format("Ticket booking failed. Place %s is already booked.", ticket.getPlace()));
      throw new ApplicationException("Ticket booking failed", HttpStatus.BAD_REQUEST);
    }

    UserAccount account = userAccountService.getUserAccountByUserId(userId);
    Event event = eventService.getEventById(eventId);

    // check and withdraw money for ticket
    if (!userAccountService.withdrawFromUserAccount(account.getId(), event.getTicketPrice())) {
      LOGGER.error("Ticket booking failed. Not enough money.");
      throw new ApplicationException("Ticket booking failed", HttpStatus.BAD_REQUEST);
    }

    Ticket bookedTicket = ticketRepository.save(ticket);
    LOGGER.info("Ticket booked successfully. Ticket details: " + bookedTicket.toString());

    return ticketRepository.findById(bookedTicket.getId());
  }

  public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {

    List<Ticket> tickets = new ArrayList<>();

    for (Ticket ticket : ticketRepository.findAll()) {
      if (ticket.getUserId() == user.getId()) {
        tickets.add(ticket);
      }
    }

    tickets.sort(
        (t1, t2) ->
            eventRepository
                .findAll()
                .get((int) t2.getEventId() - 1)
                .getDate()
                .compareTo(eventRepository.findAll().get((int) t1.getEventId() - 1).getDate()));

    LOGGER.info(String.format("%s ticket(s) found: ", tickets.size()));
    tickets.forEach(LOGGER::info);

    return tickets;
  }

  public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {

    List<Ticket> tickets = new ArrayList<>();

    for (Ticket ticket : ticketRepository.findAll()) {
      if (ticket.getEventId() == event.getId()) {
        tickets.add(ticket);
      }
    }
    tickets.sort(
        Comparator.comparing(
            t -> userRepository.findAll().get((int) t.getUserId() - 1).getEmail()));

    LOGGER.info(String.format("%s ticket(s) found: ", tickets.size()));
    tickets.forEach(LOGGER::info);

    return tickets;
  }

  public boolean cancelTicket(long ticketId) {

    boolean isTicketCanceled = false;

    if (ticketRepository.findById(ticketId) != null) {
      ticketRepository.deleteById(ticketId);
      isTicketCanceled = true;
    }

    LOGGER.info("Ticket canceled: " + isTicketCanceled);

    return isTicketCanceled;
  }

  // private method for place check
  private boolean isPlaceFree(Ticket ticket) {

    boolean isPlaceFree = true;

    for (Ticket storedTicket : ticketRepository.findAll()) {
      if (storedTicket.getEventId() == ticket.getEventId()
          && storedTicket.getPlace() == ticket.getPlace()) {
        isPlaceFree = false;
      }
    }

    return isPlaceFree;
  }

  public void preloadTickets() {

    try {
      // parse tickets from xml file
      List<XMLTicket> tickets = objXMLMapper.xmlToObj();

      // book each XMLTicket (id is assigned and ticket is stored to the storage)
      tickets.forEach(
          ticket ->
              bookTicket(
                  ticket.getUserId(),
                  ticket.getEventId(),
                  ticket.getPlace(),
                  ticket.getCategory()));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Ticket> preloadTicketsFromFile(FileInputStream fileInputStream) {

    List<Ticket> freshTickets = new ArrayList<>();
    try {
      // parse tickets from xml file
      List<XMLTicket> tickets = objXMLMapper.xmlToObjFromFile(fileInputStream);

      // book each XMLTicket (id is assigned and ticket is stored to the storage)
      tickets.forEach(
          ticket ->
              freshTickets.add(
                  bookTicket(
                      ticket.getUserId(),
                      ticket.getEventId(),
                      ticket.getPlace(),
                      ticket.getCategory())));

    } catch (IOException e) {
      e.printStackTrace();
    }
    return freshTickets;
  }
}
