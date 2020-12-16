package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.UserAccount;
import com.epam.jgmp.repository.UserAccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  private static final Log LOGGER = LogFactory.getLog(UserAccountService.class);
  private final UserAccountRepository userAccountRepository;

  @Autowired
  private UserAccountService(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  public UserAccount getUserAccountById(long accountId) {

    UserAccount userAccount = userAccountRepository.findById(accountId);

    if (userAccount == null) {
      LOGGER.error("Account not found.");
      throw new ApplicationException("Account not found", HttpStatus.NOT_FOUND);
    }

    LOGGER.info("Account found: " + userAccount.getId());

    return userAccount;
  }

  public UserAccount getUserAccountByUserId(long userId) {

    for (UserAccount userAccount : userAccountRepository.findAll()) {
      if (userAccount.getUserId() == userId) {
        LOGGER.info("Account found: " + userAccount.getId());
        return userAccount;
      }
    }
    LOGGER.error("Account not found.");
    throw new ApplicationException("Account not found", HttpStatus.NOT_FOUND);
  }

  public UserAccount createUserAccount(UserAccount userAccount) {

    UserAccount savedAccount = userAccountRepository.save(userAccount);
    LOGGER.info("Account created successfully. " + userAccount.getId());

    return userAccountRepository.findById(savedAccount.getId());
  }

  public boolean deleteUserAccount(long accountId) {

    boolean isUserDeleted = false;

    if (userAccountRepository.findById(accountId) != null) {
      userAccountRepository.deleteById(accountId);
      isUserDeleted = true;
    }
    LOGGER.info("Account deleted: " + isUserDeleted);

    return isUserDeleted;
  }

  public boolean refillUserAccount(long accountId, double amount) {

    boolean isAccountRefilled = false;

    if (userAccountRepository.findById(accountId) == null) {
      LOGGER.error("Account not refilled because not found.");
      throw new ApplicationException("Account not refilled", HttpStatus.NOT_FOUND);
    }

    UserAccount account = userAccountRepository.findById(accountId);

    if (amount > 0) {
      account.setMoney(account.getMoney() + amount);
      userAccountRepository.save(account);
      isAccountRefilled = true;
    }
    LOGGER.info("Account refilled: " + isAccountRefilled);

    return isAccountRefilled;
  }

  public boolean withdrawFromUserAccount(long accountId, double ticketPrice) {

    boolean isWithdrawSuccessful = false;

    if (userAccountRepository.findById(accountId) == null) {
      LOGGER.error("Withdrawn not successful because account not found.");
      throw new ApplicationException("Account not found", HttpStatus.NOT_FOUND);
    }

    UserAccount account = userAccountRepository.findById(accountId);

    if (account.getMoney() >= ticketPrice) {
      account.setMoney(account.getMoney() - ticketPrice);
      userAccountRepository.save(account);
      isWithdrawSuccessful = true;
      LOGGER.info("Money withdrawn from account.");
    } else {
      LOGGER.info("Not enough money.");
    }
    return isWithdrawSuccessful;
  }
}
