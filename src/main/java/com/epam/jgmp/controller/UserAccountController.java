package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.UserAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class UserAccountController {

  private BookingFacade bookingFacade;

  public UserAccountController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @GetMapping("/getByAccountId")
  public ResponseEntity<UserAccount> getUserAccountById(@RequestParam("accountId") long accountId) {

    UserAccount userAccount = bookingFacade.getUserAccountById(accountId);

    return new ResponseEntity<>(userAccount, HttpStatus.OK);
  }

  @GetMapping("/getByUserId")
  public ResponseEntity<UserAccount> getUserAccountByUserId(@RequestParam("userId") long userId) {

    UserAccount userAccount = bookingFacade.getUserAccountByUserId(userId);

    return new ResponseEntity<>(userAccount, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<UserAccount> createUserAccount(
      @RequestParam("userId") long userId, @RequestParam("money") double money) {

    UserAccount userAccount = bookingFacade.createUserAccount(new UserAccount(userId, money));

    return new ResponseEntity<>(userAccount, HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> deleteUserAccount(@RequestParam("userId") long accountId) {

    boolean result = bookingFacade.deleteUserAccount(accountId);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping("/refill")
  public ResponseEntity<Boolean> refillUserAccount(
      @RequestParam("accountId") long accountId, @RequestParam("amount") double amount) {

    boolean result = bookingFacade.refillUserAccount(accountId, amount);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
