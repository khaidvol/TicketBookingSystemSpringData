package com.epam.jgmp.xml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ObjXMLMapperTest {

  @Autowired ObjXMLMapper objXMLMapper;

  @Test
  void xmlToObj() throws IOException {
    List<XMLTicket> xmlTickets = objXMLMapper.xmlToObj();

    Assertions.assertNotNull(xmlTickets);
    Assertions.assertEquals(3, xmlTickets.size());
  }
}
