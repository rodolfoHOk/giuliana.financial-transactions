package br.com.hiokdev.financialtransactions.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.hiokdev.financialtransactions.domain.exception.JobRunException;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler{
  
  @ExceptionHandler(JobRunException.class)
  public ResponseEntity<Object> jobRunExceptionHandler(
    JobRunException exception,
    WebRequest request
  ) {
    var message = exception.getMessage();
    if (exception.getMessage().startsWith("A job instance already exists")) {
      message = "A job instance already exists";
    }
    return handleExceptionInternal(exception, message,
      new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

}
