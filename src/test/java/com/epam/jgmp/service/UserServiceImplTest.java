package com.epam.jgmp.service;

import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.repository.model.User;
import com.epam.jgmp.service.implementation.UserServiceImpl;
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
public class UserServiceImplTest {

  private static final String TEST_USER_1 = "TestUser1";
  private static final String TEST_USER_2 = "TestUser2";
  private static final String TEST_1_GMAIL_COM = "test1@gmail.com";
  private static final String TEST_2_GMAIL_COM = "test2@gmail.com";

  @Autowired private UserServiceImpl userServiceImpl;
  private User user;

  @BeforeEach
  public void setUp() {
    user = userServiceImpl.createUser(new User(TEST_USER_1, TEST_1_GMAIL_COM));
  }

  @Test
  void getUserByIdTest() {
    Assertions.assertEquals(user, userServiceImpl.getUserById(user.getId()));
  }

  @Test
  void getNotExistingUserByIdTest() {
    Assertions.assertThrows(ApplicationException.class, () -> userServiceImpl.getUserById(1000L));
  }

  @Test
  void getUserByEmailTest() {
    Assertions.assertEquals(user, userServiceImpl.getUserByEmail(user.getEmail()));
  }

  @Test
  void getNotExistingUserByEmailTest() {
    Assertions.assertThrows(
        ApplicationException.class, () -> userServiceImpl.getUserByEmail(TEST_2_GMAIL_COM));
  }

  @Test
  void getUsersByNameTest() {
    List<User> users = Collections.singletonList(user);

    Assertions.assertEquals(users, userServiceImpl.getUsersByName(user.getName(), 1, 1));
  }

  @Test
  void createUserTest() {
    Assertions.assertEquals(user, userServiceImpl.createUser(user));
  }

  @Test
  void createUserWithUsedEmailTest() {
    User user2 = new User(TEST_USER_2, TEST_1_GMAIL_COM);

    Assertions.assertThrows(ApplicationException.class, () -> userServiceImpl.createUser(user2));
  }

  @Test
  void updateUserTest() {
    user.setName(TEST_USER_2);

    Assertions.assertEquals(user, userServiceImpl.updateUser(user));
  }

  @Test
  void updateNotExistingUserTest() {
    User user2 = new User(TEST_USER_2, TEST_2_GMAIL_COM);

    Assertions.assertThrows(ApplicationException.class, () -> userServiceImpl.updateUser(user2));
  }

  @Test
  void deleteUserTest() {
    Assertions.assertTrue(userServiceImpl.deleteUser(user.getId()));
  }
}
