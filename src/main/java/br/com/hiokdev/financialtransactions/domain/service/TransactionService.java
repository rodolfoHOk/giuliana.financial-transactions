package br.com.hiokdev.financialtransactions.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hiokdev.financialtransactions.domain.entity.ReportTransaction;
import br.com.hiokdev.financialtransactions.domain.entity.Transaction;
import br.com.hiokdev.financialtransactions.domain.repository.TransactionRepository;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
 
  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Iterable<Transaction> listTotalsTransactionByStoreName() {
    var transactions = transactionRepository.findAll();
    return transactions;
  }

}
