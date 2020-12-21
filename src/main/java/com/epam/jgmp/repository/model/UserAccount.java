package com.epam.jgmp.repository.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "UserAccount")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAccount {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "USERID")
  private long userId;

  @Column(name = "MONEY")
  private double money;

  public UserAccount() {}

  public UserAccount(long userId, double money) {
    this.userId = userId;
    this.money = money;
  }

  public UserAccount(long id, long userId, double money) {
    this.id = id;
    this.userId = userId;
    this.money = money;
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

  public double getMoney() {
    return money;
  }

  public void setMoney(double money) {
    this.money = money;
  }

  @Override
  public String toString() {
    return "UserAccount{" + "id=" + id + ", userId=" + userId + ", money=" + money + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserAccount that = (UserAccount) o;

    if (id != that.id) return false;
    if (userId != that.userId) return false;
    return Double.compare(that.money, money) == 0;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (userId ^ (userId >>> 32));
    temp = Double.doubleToLongBits(money);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
