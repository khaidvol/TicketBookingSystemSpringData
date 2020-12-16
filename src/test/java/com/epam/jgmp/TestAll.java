package com.epam.jgmp;

import com.epam.jgmp.controller.EventControllerTest;
import com.epam.jgmp.controller.TicketControllerTest;
import com.epam.jgmp.controller.TicketsPdfControllerTest;
import com.epam.jgmp.controller.UserControllerTest;
import com.epam.jgmp.integration.EventControllerIntegrationTest;
import com.epam.jgmp.integration.IntegrationTest;
import com.epam.jgmp.integration.TicketControllerIntegrationTest;
import com.epam.jgmp.integration.UserControllerIntegrationTest;
import com.epam.jgmp.pdf.TicketsPdfBuilderTest;
import com.epam.jgmp.service.EventServiceTest;
import com.epam.jgmp.service.TicketServiceTest;
import com.epam.jgmp.service.UserAccountServiceTest;
import com.epam.jgmp.service.UserServiceTest;
import com.epam.jgmp.xml.ObjXMLMapperTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
  UserServiceTest.class,
  EventServiceTest.class,
  TicketServiceTest.class,
  UserAccountServiceTest.class,
  IntegrationTest.class,
  ObjXMLMapperTest.class,
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
