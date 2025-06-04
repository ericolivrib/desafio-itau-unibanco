package com.erico.desafio.itau.transaction;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.erico.desafio.itau.transaction.dto.StatisticsResponse;
import com.erico.desafio.itau.transaction.dto.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();

    /**
   * Adiciona uma nova transação na fila de transações.
   * 
   * @param transactionDto Nova transação.
   * @return UUID da nova transação.
   */
  public UUID addTransaction(TransactionRequest transactionDto) {
    OffsetDateTime now = OffsetDateTime.now();

    if (transactionDto.dateTime().isAfter(now)) {
      throw new FutureTransactionException("Transação efetuada em uma data futura: "
              + transactionDto.dateTime());
    }

    Transaction transaction = transactionDto.toModel();
    transactions.add(transaction);
    return transaction.getId();
  }

  /**
   * Retorna todas as transações presentes na fila de transações.
   * @return Fila de transações.
   */
  Queue<Transaction> getTransactions() {
    return transactions;
  }

  /**
   * Limpa todas as transações presentes na fila de transações.
   */
  public void clearTransactions() {
    transactions.clear();
  }

  /**
   * Calcula e retorna as estatísticas das transações adicionadas nos últimos
   * segundos.
   * 
   * @param interval Intervalo de busca de transações em segundos.
   * @return DTO com as estatísticas das transações.
   */
  public StatisticsResponse getStatistics(int interval) {
    OffsetDateTime now = OffsetDateTime.now();

    DoubleSummaryStatistics statistics = transactions.stream()
        .filter(t -> t.getDateTime().isAfter(now.minusSeconds(interval)))
        .mapToDouble((t) -> t.getValue().doubleValue())
        .summaryStatistics();

    if (statistics.getCount() == 0) {
      return new StatisticsResponse(0, 0, 0, 0, 0);
    }

    return new StatisticsResponse(statistics);
  }
}
