package br.com.hiokdev.financialtransactions.domain.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CNABService {

  private final Path fileStorageLocation;

  public CNABService(@Value("${file.upload-dir}") String fileUploadDir) {
    this.fileStorageLocation = Path.of(fileUploadDir);
  }

  public void uploadCNABFile(MultipartFile file) throws IllegalStateException, IOException {
    var filename = StringUtils.cleanPath(file.getOriginalFilename());
    var targetLocation = fileStorageLocation.resolve(filename);
    file.transferTo(targetLocation);
  }

}
