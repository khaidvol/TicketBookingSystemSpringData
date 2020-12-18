package com.epam.jgmp.service.pdf;

import com.epam.jgmp.repository.model.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TicketsPdfBuilderTest {

  @Autowired TicketsPdfBuilder ticketsPdfBuilder;

  @Test
  void buildPdfDocument() throws Exception {

    Document document = new Document();
    Map<String, Object> model = new HashMap<>();
    PdfWriter pdfWriter = Mockito.mock(PdfWriter.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    Ticket ticket = Mockito.mock(Ticket.class);

    Mockito.when(ticket.getUserId()).thenReturn(1L);
    Mockito.when(ticket.getEventId()).thenReturn(1L);
    Mockito.when(ticket.getPlace()).thenReturn(1);
    Mockito.when(ticket.getCategory()).thenReturn(Ticket.Category.PREMIUM);

    List<Ticket> tickets = new ArrayList<>();
    tickets.add(ticket);
    model.put("tickets", tickets);

    document.open();
    ticketsPdfBuilder.buildPdfDocument(model, document, pdfWriter, request, response);
    document.close();

    Assertions.assertNotNull(document);
  }
}
