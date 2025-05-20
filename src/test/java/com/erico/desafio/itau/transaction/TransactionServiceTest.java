package com.erico.desafio.itau.transaction;

import com.erico.desafio.itau.transaction.dto.StatisticsResponse;
import com.erico.desafio.itau.transaction.dto.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

class TransactionServiceTest {

    TransactionService underTest;

    Clock fixedClock;

    @BeforeEach
    void setUp() {
        fixedClock = Clock.fixed(Instant.parse("2025-05-20T00:00:00Z"), ZoneOffset.UTC);
        underTest = new TransactionService(fixedClock);
    }

    @Test
    @DisplayName("Deve criar uma transação")
    void shouldAddTransactionIntoTransactionsQueue() {
        // Arrange
        TransactionRequest request = new TransactionRequest(BigDecimal.valueOf(532.98), OffsetDateTime.now());

        // Act
        UUID result = underTest.addTransaction(request);

        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Dada uma data e hora futura, deve lançar uma exceção ao adicionar transação")
    void givenFutureDateTime_whenAddTransaction_thenThrowException() {
        // Arrange
        TransactionRequest request = new TransactionRequest(BigDecimal.valueOf(532.98), OffsetDateTime.now().plusMinutes(5));

        // Act & Assert
        Assertions.assertThrows(FutureTransactionException.class, () -> underTest.addTransaction(request));
    }

    @Test
    @DisplayName("Deve apagar todas as transações presentes na fila de transações")
    void shouldClearAllTransactions() {
        // Arrange
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(200.00), OffsetDateTime.now()));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(100.00), OffsetDateTime.now()));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(500.00), OffsetDateTime.now()));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(750.00), OffsetDateTime.now()));

        Assertions.assertFalse(underTest.getTransactions().isEmpty());

        // Act
        underTest.clearTransactions();

        // Assert
        Assertions.assertTrue(underTest.getTransactions().isEmpty());
    }

    @Test
    @DisplayName("Dado um intervalo, quando buscar as estatísticas, deve retornar um DTO com as estatísticas")
    void givenInterval_whenGetStatistics_thenReturnStatisticsResponse() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now(fixedClock);

        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(200.00), now.minusSeconds(5)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(300.00), now.minusSeconds(10)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(400.00), now.minusSeconds(15)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(750.00), now.minusSeconds(20)));

        int interval = 20;

        // Act
        StatisticsResponse result = underTest.getStatistics(interval);

        // Assert
        Assertions.assertEquals(3, result.count());
        Assertions.assertEquals(900.00, result.sum());
        Assertions.assertEquals(300.00, result.avg());
        Assertions.assertEquals(400.00, result.max());
        Assertions.assertEquals(200.00, result.min());
    }

    @Test
    @DisplayName("Dado um intervalo pequeno, deve retornar valores zerados ao buscar estatísticas")
    void givenSmallInterval_whenGetStatistics_thenReturnStatisticsWithZeroValues() {
        OffsetDateTime now = OffsetDateTime.now(fixedClock);

        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(200.00), now.minusSeconds(20)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(300.00), now.minusSeconds(30)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(400.00), now.minusSeconds(40)));
        underTest.addTransaction(new TransactionRequest(BigDecimal.valueOf(750.00), now.minusSeconds(50)));

        int interval = 20;

        // Act
        StatisticsResponse result = underTest.getStatistics(interval);

        // Assert
        Assertions.assertEquals(0, result.count());
        Assertions.assertEquals(0, result.sum());
        Assertions.assertEquals(0, result.avg());
        Assertions.assertEquals(0, result.max());
        Assertions.assertEquals(0, result.min());
    }

}