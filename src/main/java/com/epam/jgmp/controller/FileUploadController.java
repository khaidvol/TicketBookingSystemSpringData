package com.epam.jgmp.controller;

import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.repository.model.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileUploadController {

  private final BookingFacade bookingFacade;

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
}
