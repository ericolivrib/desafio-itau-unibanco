package com.erico.desafio.itau.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.erico.desafio.itau.model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
  @NotNull
  @Min(0)
  @JsonProperty("valor")
  Double value,

  @NotNull
  @JsonProperty("dataHora")
  OffsetDateTime dateTime
) implements TransactionRequestSpec {

  public Transaction toModel() {
    return new Transaction(UUID.randomUUID(), value, dateTime);
  }
}
