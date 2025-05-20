package com.erico.desafio.itau.service;

import com.erico.desafio.itau.dto.TransactionRequest;
import com.erico.desafio.itau.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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

}