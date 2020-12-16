package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

  private static final String TEST_USER_1 = "TestUser1";
  private static final String TEST_USER_2 = "TestUser2";
  private static final String TEST_1_GMAIL_COM = "test1@gmail.com";
  private static final String TEST_2_GMAIL_COM = "test2@gmail.com";

  @Autowired private UserService userService;
  private User user;

  @BeforeEach
  public void setUp() {
    user = userService.createUser(new User(TEST_USER_1, TEST_1_GMAIL_COM));
  }

  @Test
  void getUserByIdTest() {
    Assertions.assertEquals(user, userService.getUserById(user.getId()));
  }

  @Test
  void getNotExistingUserByIdTest() {
    Assertions.assertThrows(ApplicationException.class, () -> userService.getUserById(1000L));
  }

  @Test
  void getUserByEmailTest() {
    Assertions.assertEquals(user, userService.getUserByEmail(user.getEmail()));
  }

  @Test
  void getNotExistingUserByEmailTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userService.getUserByEmail(TEST_2_GMAIL_COM));
  }

  @Test
  void getUsersByNameTest() {
    List<User> users = Collections.singletonList(user);

    Assertions.assertEquals(users, userService.getUsersByName(user.getName(), 1, 1));
  }

  @Test
  void createUserTest() {
    Assertions.assertEquals(user, userService.createUser(user));
  }

  @Test
  void createUserWithUsedEmailTest() {
    User user2 = new User(TEST_USER_2, TEST_1_GMAIL_COM);

    Assertions.assertThrows(ApplicationException.class, () -> userService.createUser(user2));
  }

  @Test
  void updateUserTest() {
    user.setName(TEST_USER_2);

    Assertions.assertEquals(user, userService.updateUser(user));
  }

  @Test
  void updateNotExistingUserTest() {
    User user2 = new User(TEST_USER_2, TEST_2_GMAIL_COM);

    Assertions.assertThrows(ApplicationException.class, () -> userService.updateUser(user2));
  }

  @Test
  void deleteUserTest() {
    Assertions.assertTrue(userService.deleteUser(user.getId()));
  }
}
