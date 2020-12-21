package com.epam.jgmp.controller.dto;

public class UserAccountDTO {

  private long id;
  private long userId;
  private double money;

  public UserAccountDTO() {}

  public UserAccountDTO(long userId, double money) {
    this.userId = userId;
    this.money = money;
  }

  public UserAccountDTO(long id, long userId, double money) {
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
}
