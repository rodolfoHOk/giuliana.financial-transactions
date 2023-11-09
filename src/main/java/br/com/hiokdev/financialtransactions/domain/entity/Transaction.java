package br.com.hiokdev.financialtransactions.domain.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "TRANSACTIONS")
public record Transaction(
  @Id Long id,
  Integer type,
  Date date,
  BigDecimal amount,
  Long cpf,
  String card,
  Time time,
  @Column("STORE_OWNER") String storeOwner,
  @Column("STORE_NAME")String storeName
) {
  
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
