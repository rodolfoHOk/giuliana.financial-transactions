package br.com.hiokdev.financialtransactions.web;

import java.io.IOException;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.hiokdev.financialtransactions.domain.service.CNABService;

@RestController
@RequestMapping("cnab")
public class CNABController {

  private final CNABService cnabService;

  public CNABController(CNABService cnabService) {
    this.cnabService = cnabService;
  }

  @PostMapping("upload")
  public String upload(@RequestParam("file") MultipartFile file) {
    try {
      cnabService.uploadCNABFile(file);
    } catch (IllegalStateException | IOException e) {
      throw new HttpMessageNotWritableException(e.getMessage());
    }
    return "Init processing...";
  }
  
}
