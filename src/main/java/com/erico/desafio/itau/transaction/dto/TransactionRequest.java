package com.erico.desafio.itau.transaction.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.erico.desafio.itau.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
  @NotNull(message = "Valor da transação não pode estar vazio")
  @Min(value = 0, message = "Valor da transação não pode ser negativo")
  @JsonProperty("valor")
  BigDecimal value,

  @NotNull(message = "Data e hora da transação não podem estar vazios")
  @JsonProperty("dataHora")
  OffsetDateTime dateTime
) implements TransactionRequestSpec {

  public Transaction toModel() {
    return new Transaction(UUID.randomUUID(), value, dateTime);
  }
}
