package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

  public static final String RESULT = "result";
  public static final String TICKET_TEMPLATE = "ticketTemplate";
  private BookingFacade bookingFacade;

  public TicketController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @PostMapping("/book")
  public ModelAndView bookTicket(
      @RequestParam(value = "userId") Long userId,
      @RequestParam(value = "eventId") Long eventId,
      @RequestParam(value = "place") int place,
      @RequestParam(value = "category") Ticket.Category category) {

    Ticket ticket = bookingFacade.bookTicket(userId, eventId, place, category);

    ModelAndView modelAndView = new ModelAndView(TICKET_TEMPLATE);
    modelAndView.addObject(RESULT, ticket);

    return modelAndView;
  }

  @GetMapping("/userTickets")
  public ModelAndView getBookedTicketsForUser(
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "pageSize") int pageSize,
      @RequestParam(value = "pageNum") int pageNum) {

    com.epam.jgmp.model.User user = new User(id, name, email);
    List<Ticket> tickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);

    ModelAndView modelAndView = new ModelAndView(TICKET_TEMPLATE);
    modelAndView.addObject(RESULT, tickets);

    return modelAndView;
  }

  @GetMapping("/eventTickets")
  public ModelAndView getBookedTicketsForEvent(
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "title") String title,
      @RequestParam(value = "day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date day,
      @RequestParam(value = "price") double price,
      @RequestParam(value = "pageSize", required = false) int pageSize,
      @RequestParam(value = "pageNum", required = false) int pageNum) {

    Event event = new Event(id, title, day, price);
    List<Ticket> tickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);

    ModelAndView modelAndView = new ModelAndView(TICKET_TEMPLATE);
    modelAndView.addObject(RESULT, tickets);

    return modelAndView;
  }

  @DeleteMapping("/cancel")
  public String cancelTicket(@RequestParam(value = "id") Long id, Model model) {

    Boolean deleteResult = bookingFacade.cancelTicket(id);
    model.addAttribute(RESULT, String.format("Ticket #%s canceled: %s", id, deleteResult));

    return TICKET_TEMPLATE;
  }

  @GetMapping("/preload")
  public String preloadTicket(Model model) {

    bookingFacade.preloadTickets();
    model.addAttribute(RESULT, "Tickets preloaded");
    return TICKET_TEMPLATE;
  }
}
