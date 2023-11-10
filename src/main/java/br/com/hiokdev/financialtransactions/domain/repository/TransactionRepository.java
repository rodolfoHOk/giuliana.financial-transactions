package br.com.hiokdev.financialtransactions.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.hiokdev.financialtransactions.domain.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
  List<Transaction> findAllByOrderByStoreNameAscIdDesc();
}
