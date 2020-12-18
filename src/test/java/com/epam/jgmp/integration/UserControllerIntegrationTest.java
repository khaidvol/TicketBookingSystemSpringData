package com.epam.jgmp.integration;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private BookingFacade bookingFacade;

  private User user;
  private List<User> users;

  private ObjectMapper objectMapper;
  private ObjectNode objectNode;

  @BeforeEach
  public void setUp() {

    objectMapper = new ObjectMapper();
    objectNode = objectMapper.createObjectNode();

    String name = "TestUser";
    String email = "test666@gmail.com";
    user = bookingFacade.createUser(new User(name, email));
    users = Collections.singletonList(user);
  }

  @Test
  void getUserById() throws Exception {

    long id = user.getId();

    this.mockMvc
        .perform(get("/user/id?id={id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getUserById(id)));
  }

  @Test
  void getUserByEmail() throws Exception {

    String email = user.getEmail();

    this.mockMvc
        .perform(get("/user/email?email={email}", email))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getUserByEmail(email)));
  }

  @Test
  void getUsersByName() throws Exception {

    String name = user.getName();

    this.mockMvc
        .perform(get("/user/name?name={name}&pageSize=1&pageNum=1", name))
        .andExpect(status().isOk())
        .andExpect(view().name("userTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getUsersByName(name, 1, 1)));
  }

  @Test
  void createUser() throws Exception {
    bookingFacade.deleteUser(user.getId());

    objectNode.put("name", user.getName());
    objectNode.put("email", user.getEmail());

    this.mockMvc
        .perform(
            post("/user/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void updateUser() throws Exception {

    objectNode.put("id", user.getId());
    objectNode.put("name", "TestUser2");
    objectNode.put("email", "test2@gmail.com");

    this.mockMvc
        .perform(
            put("/user/update/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void deleteUser() throws Exception {

    this.mockMvc
        .perform(delete("/user/delete/{id}", user.getId()))
        .andExpect(status().isOk())
        .andReturn();
  }
}
