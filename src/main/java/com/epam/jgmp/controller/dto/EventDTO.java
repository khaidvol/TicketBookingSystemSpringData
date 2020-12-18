package com.epam.jgmp.controller.dto;

import java.util.Date;

public class EventDTO {

  private long id;
  private String title;
  private Date date;
  private double ticketPrice;

  public EventDTO() {}

  public EventDTO(String title, Date date, double ticketPrice) {
    this.title = title;
    this.date = date;
    this.ticketPrice = ticketPrice;
  }

  public EventDTO(long id, String title, Date date, double ticketPrice) {
    this.id = id;
    this.title = title;
    this.date = date;
    this.ticketPrice = ticketPrice;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public double getTicketPrice() {
    return ticketPrice;
  }

  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }
}
