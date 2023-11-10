package br.com.hiokdev.financialtransactions.domain.entity;

import java.math.BigDecimal;
import java.util.List;

public record ReportTransaction(
  String storeName,
  BigDecimal total,
  List<Transaction> transactions
) {
  
  public ReportTransaction addTotal(BigDecimal amount) {
    return new ReportTransaction(storeName, total.add(amount), transactions);
  }

  public ReportTransaction addTransaction(Transaction transaction) {
    transactions.add(transaction);
    return new ReportTransaction(storeName, total, transactions);
  }

}
