package br.com.hiokdev.financialtransactions.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.financialtransactions.domain.entity.ReportTransaction;
import br.com.hiokdev.financialtransactions.domain.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionController {

  private final TransactionService transactionService;
  
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping
  @CrossOrigin(origins = {"http://localhost:9090"})
  public List<ReportTransaction> listAll() {
    return transactionService.listTotalsTransactionByStoreName();
  }
  
}
