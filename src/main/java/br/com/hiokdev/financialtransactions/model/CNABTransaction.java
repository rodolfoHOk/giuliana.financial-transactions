package br.com.hiokdev.financialtransactions.model;

import java.math.BigDecimal;

public record CNABTransaction(
  Integer type,
  String date,
  BigDecimal amount,
  Long cpf,
  String card,
  String time,
  String storeOwner,
  String storeName
) {

}
