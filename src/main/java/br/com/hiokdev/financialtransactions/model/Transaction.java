package br.com.hiokdev.financialtransactions.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public record Transaction(
  Long id,
  Integer type,
  Date date,
  BigDecimal amount,
  Long cpf,
  String card,
  Time time,
  String storeOwner,
  String storeName
) {
  
  public Transaction withAmount(BigDecimal amount) {
    return new Transaction(
      id,
      type,
      date,
      amount,
      cpf,
      card,
      time,
      storeOwner,
      storeName);
  }

  public Transaction withDate(String date) throws ParseException {
    var dateFormat = new SimpleDateFormat("yyyyMMdd");
    var dateParsed = dateFormat.parse(date);

    return new Transaction(
      id,
      type,
      new Date(dateParsed.getTime()),
      amount,
      cpf,
      card,
      time,
      storeOwner,
      storeName);
  }

  public Transaction withTime(String time) throws ParseException {
    var dateFormat = new SimpleDateFormat("HHmmss");
    var timeParsed = dateFormat.parse(time);

    return new Transaction(
      id,
      type,
      date,
      amount,
      cpf,
      card,
      new Time(timeParsed.getTime()),
      storeOwner,
      storeName);
  }

}
