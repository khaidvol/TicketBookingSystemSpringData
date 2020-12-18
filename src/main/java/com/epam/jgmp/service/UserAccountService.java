package com.epam.jgmp.service;

import com.epam.jgmp.repository.model.UserAccount;

public interface UserAccountService {

  /**
   * Gets user account by its id.
   *
   * @return UserAccount.
   */
  UserAccount getUserAccountById(long accountId);

  /**
   * Gets user account by user id.
   *
   * @return UserAccount.
   */
  UserAccount getUserAccountByUserId(long userId);

  /**
   * Creates new user account. UserAccount id should be auto-generated.
   *
   * @param userAccount UserAccount data.
   * @return Created UserAccount object.
   */
  UserAccount createUserAccount(UserAccount userAccount);

  /**
   * Deletes user account by its id.
   *
   * @param accountId User id.
   * @return Flag that shows whether user account has been deleted.
   */
  boolean deleteUserAccount(long accountId);

  /**
   * Refills user account with provided amount.
   *
   * @param accountId User id.
   * @param amount Amount of money to refill.
   * @return Flag that shows whether user account has been refilled.
   */
  boolean refillUserAccount(long accountId, double amount);

  /**
   * Withdraws money from user account.
   *
   * @param accountId User id.
   * @param ticketPrice price for event ticket.
   * @return Flag that shows whether money have been withdrawn from the account.
   */
  boolean withdrawFromUserAccount(long accountId, double ticketPrice);
}
