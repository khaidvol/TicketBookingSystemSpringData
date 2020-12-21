package com.epam.jgmp.repository.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Ticket")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "EVENT_ID")
  private long eventId;

  @Column(name = "PLACE")
  private int place;

  @Column(name = "CATEGORY")
  private Category category;

  public Ticket() {}

  public Ticket(long userId, long eventId, int place, Category category) {
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

  public long getEventId() {
    return eventId;
  }

  public void setEventId(long eventId) {
    this.eventId = eventId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public int getPlace() {
    return place;
  }

  public void setPlace(int place) {
    this.place = place;
  }

  public String toString() {
    return "Ticket: "
        + "id="
        + id
        + ", eventId="
        + eventId
        + ", userId="
        + userId
        + ", place="
        + place
        + ", category="
        + category
        + '.';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Ticket ticket = (Ticket) o;

    if (id != ticket.id) return false;
    if (userId != ticket.userId) return false;
    if (eventId != ticket.eventId) return false;
    if (place != ticket.place) return false;
    return category == ticket.category;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (userId ^ (userId >>> 32));
    result = 31 * result + (int) (eventId ^ (eventId >>> 32));
    result = 31 * result + place;
    result = 31 * result + (category != null ? category.hashCode() : 0);
    return result;
  }

  public enum Category {
    STANDARD,
    PREMIUM,
    BAR
  }
}
