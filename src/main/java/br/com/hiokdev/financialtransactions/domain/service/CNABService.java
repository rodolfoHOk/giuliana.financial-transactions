package br.com.hiokdev.financialtransactions.domain.service;

import java.nio.file.Path;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.hiokdev.financialtransactions.domain.exception.JobRunException;
import br.com.hiokdev.financialtransactions.domain.exception.UploadFileException;

@Service
public class CNABService {

  private final Path fileStorageLocation;
  private final JobLauncher jobLauncher;
  private final Job job;

  public CNABService(
    @Value("${file.upload-dir}") String fileUploadDir,
    @Qualifier("jobLauncherAsync") JobLauncher jobLauncher,
    Job job
  ) {
    this.fileStorageLocation = Path.of(fileUploadDir);
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  public void uploadCNABFile(MultipartFile file) {
    var filename = StringUtils.cleanPath(file.getOriginalFilename());
    var targetLocation = fileStorageLocation.resolve(filename);
    try {
      file.transferTo(targetLocation);
    } catch (Exception e) {
      throw new UploadFileException(e.getMessage());
    }
    
    var jobParameter = new JobParametersBuilder()
      .addJobParameter("cnab", file.getOriginalFilename(),
        String.class, true)
      .addJobParameter("cnabFile", "file:" + targetLocation.toString(),
        String.class)
      .toJobParameters();
    try {
      jobLauncher.run(job, jobParameter);
    } catch (Exception e) {
      throw new JobRunException(e.getMessage());
    }
  }

}
