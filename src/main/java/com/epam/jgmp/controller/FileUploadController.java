package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileUploadController {

  private BookingFacade bookingFacade;

  public FileUploadController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @GetMapping("/uploadForm")
  public String displayForm() {

    return "fileUploadForm";
  }

  @PostMapping("/uploadFile")
  public String submit(@RequestParam("file") final MultipartFile file, final ModelMap modelMap)
      throws IOException {

    FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
    List<Ticket> tickets = bookingFacade.preloadTicketsFromFile(fileInputStream);

    modelMap.addAttribute("file", file);
    modelMap.addAttribute("tickets", tickets);

    return "fileUploadView";
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> uploadFailed(Exception exc) {
    return ResponseEntity.unprocessableEntity().build();
  }
}
