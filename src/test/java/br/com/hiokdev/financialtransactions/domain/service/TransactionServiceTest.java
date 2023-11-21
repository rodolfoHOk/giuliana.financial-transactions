package br.com.hiokdev.financialtransactions.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.hiokdev.financialtransactions.domain.entity.Transaction;
import br.com.hiokdev.financialtransactions.domain.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionService transactionService;
  
  @Test
  public void testListTotalsTransactionByStoreName() {
    // Arrange
    final String storeA = "Store A", storeB = "Store B";

    var transaction1 = new Transaction(
      1L,
      1,
      new Date(System.currentTimeMillis()),
      BigDecimal.valueOf(100),
      12345678901L,
      "1234-5678-9012-2356",
      new Time(System.currentTimeMillis()),
      "Store Owner A",
      storeA
    );

    var transaction2 = new Transaction(
      2L,
      1,
      new Date(System.currentTimeMillis()),
      BigDecimal.valueOf(50),
      98765432109L,
      "9876-5432-1098-7654",
      new Time(System.currentTimeMillis()),
      "Store Owner B",
      storeB
    );

    var transaction3 = new Transaction(
      3L,
      1,
      new Date(System.currentTimeMillis()),
      BigDecimal.valueOf(75),
      11122233302L,
      "1111-2222-3333-4444",
      new Time(System.currentTimeMillis()),
      "Store Owner A",
      storeA
    );

    var transactionsMock = List.of(transaction1, transaction2, transaction3);

    when(transactionRepository.findAllByOrderByStoreNameAscIdDesc())
      .thenReturn(transactionsMock);

    // Act
    var reports = transactionService.listTotalsTransactionByStoreName();

    // Assert
    assertEquals(2, reports.size());

    reports.forEach(report -> {
      if (report.storeName().equals(storeA)) {
        assertEquals(2, report.transactions().size());
        assertEquals(BigDecimal.valueOf(175), report.total());
        assertTrue(report.transactions().contains(transaction1));
        assertTrue(report.transactions().contains(transaction3));
      } else if (report.storeName().equals(storeB)) {
        assertEquals(1, report.transactions().size());
        assertEquals(BigDecimal.valueOf(50), report.total());
        assertTrue(report.transactions().contains(transaction2));
      }
    });
  }
}
