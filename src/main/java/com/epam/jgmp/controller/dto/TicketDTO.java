package com.epam.jgmp.controller.dto;

import com.epam.jgmp.repository.model.Ticket;

public class TicketDTO {

  private long id;
  private long userId;
  private long eventId;
  private int place;
  private Ticket.Category category;

  public TicketDTO() {}

  public TicketDTO(long userId, long eventId, int place, Ticket.Category category) {
    this.userId = userId;
    this.eventId = eventId;
    this.place = place;
    this.category = category;
  }

  public TicketDTO(long id, long userId, long eventId, int place, Ticket.Category category) {
    this.id = id;
    this.userId = userId;
    this.eventId = eventId;
    this.place = place;
    this.category = category;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getEventId() {
    return eventId;
  }

  public void setEventId(long eventId) {
    this.eventId = eventId;
  }

  public int getPlace() {
    return place;
  }

  public void setPlace(int place) {
    this.place = place;
  }

  public Ticket.Category getCategory() {
    return category;
  }

  public void setCategory(Ticket.Category category) {
    this.category = category;
  }
}
