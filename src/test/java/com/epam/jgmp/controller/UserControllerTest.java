package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private BookingFacade bookingFacade;

  private User user;
  List<User> users;

  @BeforeEach
  public void setUp() {

    long id = 1L;
    String name = "TestUser";
    String email = "test@gmail.com";
    user = new User(name, email);
    user.setId(id);
    users = Collections.singletonList(user);

    Mockito.when(bookingFacade.getUserById(id)).thenReturn(user);
    Mockito.when(bookingFacade.getUserByEmail(email)).thenReturn(user);
    Mockito.when(bookingFacade.getUsersByName(name, 1, 1)).thenReturn(users);
    Mockito.when(bookingFacade.createUser(ArgumentMatchers.any(User.class))).thenReturn(user);
    Mockito.when(bookingFacade.updateUser(ArgumentMatchers.any(User.class))).thenReturn(user);
    Mockito.when(bookingFacade.deleteUser(id)).thenReturn(true);
  }

  @Test
  void getUserById() throws Exception {

    long id = 1L;

    this.mockMvc
        .perform(get("/user/id?id={id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", user));
  }

  @Test
  void getUserByEmail() throws Exception {

    String email = "test@gmail.com";

    this.mockMvc
        .perform(get("/user/email?email={email}", email))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", user));
  }

  @Test
  void getUsersByName() throws Exception {

    String name = "TestUser";

    this.mockMvc
        .perform(get("/user/name?name={name}&pageSize=1&pageNum=1", name))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", users));
  }

  @Test
  void createUser() throws Exception {

    String name = "TestUser";
    String email = "test@gmail.com";

    this.mockMvc
        .perform(post("/user/new?name={name}&email={email}", name, email))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", "User created: ".concat(user.toString())));
  }

  @Test
  void updateUser() throws Exception {

    long id = 1L;
    String name = "TestUser";
    String email = "test@gmail.com";

    this.mockMvc
        .perform(put("/user/update?id={id}&name={name}&email={email}", id, name, email))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", "User updated: ".concat(user.toString())));
  }

  @Test
  void deleteUser() throws Exception {

    Long id = 1L;

    this.mockMvc
        .perform(delete(String.format("/user/delete?id=%s", id)))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model().attribute("result", hasToString(String.format("User #%s deleted: true", id))));
  }
}
