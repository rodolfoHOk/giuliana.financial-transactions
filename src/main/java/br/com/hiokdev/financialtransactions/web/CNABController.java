package br.com.hiokdev.financialtransactions.web;

import org.springframework.web.bind.annotation.CrossOrigin;
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
  @CrossOrigin(origins = {"http://localhost:9090"})
  public String upload(@RequestParam("file") MultipartFile file) {
    cnabService.uploadCNABFile(file);
    return "Init processing...";
  }
  
}
