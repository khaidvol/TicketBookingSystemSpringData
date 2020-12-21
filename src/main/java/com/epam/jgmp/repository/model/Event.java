package com.epam.jgmp.repository.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Event")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "TITLE")
  private String title;

  @Temporal(TemporalType.DATE)
  @Column(name = "DATE")
  private Date date;

  @Column(name = "TICKET_PRICE")
  private double ticketPrice;

  public Event() {}

  public Event(String title, Date date, double ticketPrice) {
    this.title = title;
    this.date = date;
    this.ticketPrice = ticketPrice;
  }

  // constructor for updating event
  public Event(long id, String title, Date date, double ticketPrice) {
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

  @Override
  public String toString() {
    return "Event: "
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", date="
        + date
        + ", ticket price="
        + ticketPrice
        + '.';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Event event = (Event) o;

    if (id != event.id) return false;
    if (Double.compare(event.ticketPrice, ticketPrice) != 0) return false;
    if (title != null ? !title.equals(event.title) : event.title != null) return false;
    return date != null ? date.equals(event.date) : event.date == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (int) (id ^ (id >>> 32));
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    temp = Double.doubleToLongBits(ticketPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
