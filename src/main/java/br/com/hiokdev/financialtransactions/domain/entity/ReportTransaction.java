package br.com.hiokdev.financialtransactions.domain.entity;

import java.math.BigDecimal;
import java.util.List;

public record ReportTransaction(
  String storeOwner,
  BigDecimal total,
  List<Transaction> transactions
) {
  
}
