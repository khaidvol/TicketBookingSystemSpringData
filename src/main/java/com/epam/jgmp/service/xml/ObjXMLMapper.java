package com.epam.jgmp.service.xml;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ObjXMLMapper {

  private Jaxb2Marshaller marshaller;

  public void setMarshaller(Jaxb2Marshaller marshaller) {
    this.marshaller = marshaller;
  }

  // Converting XML to an object graph (unmarshalling)
  public List<XMLTicket> xmlToObjFromFile(FileInputStream fileInputStream) throws IOException {

    XMLTicketListContainer ticketList;

    try (FileInputStream is = fileInputStream) {
      ticketList = (XMLTicketListContainer) this.marshaller.unmarshal(new StreamSource(is));
    }

    return ticketList.getTicketList();
  }
}
