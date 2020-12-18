package com.epam.jgmp;

import com.epam.jgmp.controller.EventControllerTest;
import com.epam.jgmp.controller.TicketControllerTest;
import com.epam.jgmp.controller.TicketsPdfControllerTest;
import com.epam.jgmp.controller.UserControllerTest;
import com.epam.jgmp.integration.EventControllerIntegrationTest;
import com.epam.jgmp.integration.IntegrationTest;
import com.epam.jgmp.integration.TicketControllerIntegrationTest;
import com.epam.jgmp.integration.UserControllerIntegrationTest;
import com.epam.jgmp.service.EventServiceImplTest;
import com.epam.jgmp.service.TicketServiceImplTest;
import com.epam.jgmp.service.UserAccountServiceImplTest;
import com.epam.jgmp.service.UserServiceImplTest;
import com.epam.jgmp.service.pdf.TicketsPdfBuilderTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
  UserServiceImplTest.class,
  EventServiceImplTest.class,
  TicketServiceImplTest.class,
  UserAccountServiceImplTest.class,
  IntegrationTest.class,
  TicketsPdfBuilderTest.class,
  UserControllerTest.class,
  EventControllerTest.class,
  TicketControllerTest.class,
  TicketsPdfControllerTest.class,
  UserControllerIntegrationTest.class,
  EventControllerIntegrationTest.class,
  TicketControllerIntegrationTest.class,
})
public class TestAll {}
