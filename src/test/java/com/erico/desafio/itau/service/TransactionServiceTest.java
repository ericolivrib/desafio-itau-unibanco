package com.erico.desafio.itau.service;

import com.erico.desafio.itau.dto.TransactionRequest;
import com.erico.desafio.itau.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

class TransactionServiceTest {

    TransactionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TransactionService();
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

}