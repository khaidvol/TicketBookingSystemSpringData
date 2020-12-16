package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.UserAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAccountServiceTest {

  @Autowired UserAccountService userAccountService;
  UserAccount userAccount;

  @BeforeEach
  public void setUp() {
    userAccount = userAccountService.createUserAccount(new UserAccount(1L, 1000));
  }

  @Test
  void getUserAccountByIdTest() {
    Assertions.assertEquals(
        userAccount, userAccountService.getUserAccountById(userAccount.getId()));
  }

  @Test
  void getUserAccountByNotExistingIdTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userAccountService.getUserAccountById(100L));
  }

  @Test
  void getUserAccountByUserIdTest() {
    Assertions.assertEquals(
        userAccount, userAccountService.getUserAccountByUserId(userAccount.getUserId()));
  }

  @Test
  void getUserAccountByNotExistingUserIdTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userAccountService.getUserAccountByUserId(100L));
  }

  @Test
  void createUserAccountTest() {
    Assertions.assertEquals(userAccount, userAccountService.createUserAccount(userAccount));
  }

  @Test
  void deleteUserAccountTest() {
    Assertions.assertTrue(userAccountService.deleteUserAccount(userAccount.getUserId()));
  }

  @Test
  void refillUserAccountTest() {
    Assertions.assertTrue(userAccountService.refillUserAccount(1L, 100));
  }

  @Test
  void refillNotExistingUserAccountTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userAccountService.refillUserAccount(100L, 100));
  }

  @Test
  void withdrawFromUserAccountTest() {
    Assertions.assertTrue(userAccountService.withdrawFromUserAccount(1L, 50));
  }

  @Test
  void withdrawFromNotExistingUserAccountTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userAccountService.withdrawFromUserAccount(100L, 50));
  }

  @Test
  void withdrawTooMuchFromUserAccountTest() {
    Assertions.assertFalse(userAccountService.withdrawFromUserAccount(1L, 10000));
  }
}
