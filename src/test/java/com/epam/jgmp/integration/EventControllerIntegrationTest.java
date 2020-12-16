package com.epam.jgmp.integration;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private BookingFacade bookingFacade;

  Event event;
  List<Event> events;

  @BeforeEach
  public void setUp() throws ParseException {

    String title = "TestEvent";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateInString = "2020-12-12";
    Date date = formatter.parse(dateInString);
    double price = 25;

    event = bookingFacade.createEvent(new Event(title, date, price));
    events = Collections.singletonList(event);
  }

  @Test
  void getEventById() throws Exception {

    long id = 1L;

    this.mockMvc
        .perform(get("/event/id?id={id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getEventById(id)));
  }

  @Test
  void getEventsByTitle() throws Exception {

    String title = "Disco";

    this.mockMvc
        .perform(get("/event/title?title={title}&pageSize=1&pageNum=1", title))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getEventsByTitle(title, 1, 1)));
  }

  @Test
  void getEventsForDay() throws Exception {

    String day = "2020-12-12";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = dateFormat.parse(day);

    this.mockMvc
        .perform(get("/event/day?day={day}&pageSize=1&pageNum=1", day))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(model().attribute("result", bookingFacade.getEventsForDay(date, 1, 1)));
  }

  @Test
  void createEvent() throws Exception {

    String title = "Test1Event";
    String day = "2020-12-12";
    double price = 30.55;

    this.mockMvc
        .perform(post("/event/new?title={title}&day={day}&price={price}", title, day, price))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"));
  }

  @Test
  void updateEvent() throws Exception {

    long id = event.getId();
    String title = "Test2Event";
    String day = "2020-12-12";
    double price = 66.6;

    this.mockMvc
        .perform(
            put(
                "/event/update?id={id}&title={title}&day={day}&price={price}",
                id,
                title,
                day,
                price))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"));
  }

  @Test
  void deleteEvent() throws Exception {

    long id = event.getId();

    this.mockMvc
        .perform(delete(String.format("/event/delete?id=%s", id)))
        .andExpect(status().isOk())
        .andExpect(view().name("eventTemplate"))
        .andExpect(model().attributeExists("result"))
        .andExpect(
            model().attribute("result", hasToString(String.format("Event #%s deleted: true", id))));
  }
}
