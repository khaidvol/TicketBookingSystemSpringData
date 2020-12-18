package com.epam.jgmp.controller;

import com.epam.jgmp.controller.dto.TicketDTO;
import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Event;
import com.epam.jgmp.repository.model.Ticket;
import com.epam.jgmp.repository.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

  public static final String RESULT = "result";
  public static final String TICKET_TEMPLATE = "ticketTemplate";
  private final BookingFacade bookingFacade;

  public TicketController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @PostMapping("/book")
  public Ticket bookTicket(@RequestBody TicketDTO ticketDTO) {

    return bookingFacade.bookTicket(
        ticketDTO.getUserId(), ticketDTO.getEventId(), ticketDTO.getPlace(), ticketDTO.getCategory());
  }

  @GetMapping("/userTickets")
  public ModelAndView getBookedTicketsForUser(
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "pageSize") int pageSize,
      @RequestParam(value = "pageNum") int pageNum) {

    User user = new User(id, name, email);
    List<Ticket> tickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);

    return new ModelAndView(TICKET_TEMPLATE, RESULT, tickets);
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

    return new ModelAndView(TICKET_TEMPLATE, RESULT, tickets);
  }

  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<Long> cancelTicket(@PathVariable Long id) {

    boolean isCanceled = bookingFacade.cancelTicket(id);
    if (!isCanceled) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(id, HttpStatus.OK);
  }
}
