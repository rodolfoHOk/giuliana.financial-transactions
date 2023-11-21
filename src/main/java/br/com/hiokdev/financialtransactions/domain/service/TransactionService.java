package br.com.hiokdev.financialtransactions.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

  public List<ReportTransaction> listTotalsTransactionByStoreName() {
    var transactions = transactionRepository.findAllByOrderByStoreNameAscIdDesc();

    Map<String, ReportTransaction> reportMap = new LinkedHashMap<String, ReportTransaction>();

    transactions.forEach(transaction -> {
      String storeName = transaction.storeName();
      BigDecimal amount = transaction.amount();

      reportMap.compute(storeName, (key, existingReport) -> {
        var report = (existingReport != null) 
          ? existingReport 
          : new ReportTransaction(key, BigDecimal.ZERO, new ArrayList<Transaction>());

          var transactionWithNewAmount = transaction.withAmount(amount);
          return report.addTotal(amount).addTransaction(transactionWithNewAmount);
      });

    });

    return new ArrayList<>(reportMap.values());
  }

}
