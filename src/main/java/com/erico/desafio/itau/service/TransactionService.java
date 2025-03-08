package com.erico.desafio.itau.service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import com.erico.desafio.itau.model.Transaction;

@Service
public class TransactionService {

  private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();

  /**
   * Adiciona uma nova transasão na fila de transações.
   * 
   * @param transaction Nova transação.
   * @return UUID da nova transação.
   */
  public UUID addTransaction(Transaction transaction) {
    transactions.add(transaction);
    return transaction.getId();
  }

  /**
   * Limpa todas as transações presentes na fila de transações.
   */
  public void clearTransactions() {
    transactions.clear();
  }

  /**
   * Calcula e retorna as estatísticas das transações adicionadas nos últimos 60
   * segundos.
   * 
   * @return Estatísticas das transações.
   */
  public DoubleSummaryStatistics getStatistics() {
    var now = OffsetDateTime.now();

    return transactions.stream()
        .filter(t -> t.getDateTime().isAfter(now.minusSeconds(60)))
        .mapToDouble(Transaction::getValue)
        .summaryStatistics();
  }
}
