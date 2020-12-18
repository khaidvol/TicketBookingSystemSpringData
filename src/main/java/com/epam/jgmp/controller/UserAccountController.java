package com.epam.jgmp.controller;

import com.epam.jgmp.controller.dto.UserAccountDTO;
import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.UserAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class UserAccountController {

  private final BookingFacade bookingFacade;

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
  public UserAccount createUserAccount(@RequestBody UserAccountDTO userAccountDTO) {

    UserAccount userAccount = new UserAccount(userAccountDTO.getUserId(), userAccountDTO.getMoney());

    return bookingFacade.createUserAccount(userAccount);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Long> deleteAccount(@PathVariable Long id) {

    boolean isDeleted = bookingFacade.deleteUserAccount(id);
    if (!isDeleted) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @PutMapping("/refill/{id}")
  public ResponseEntity<Boolean> refillUserAccount(
          @RequestBody UserAccountDTO userAccountDTO, @PathVariable Long id) {

    boolean result = bookingFacade.refillUserAccount(userAccountDTO.getUserId(), userAccountDTO.getMoney());

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
