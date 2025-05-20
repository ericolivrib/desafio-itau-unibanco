package com.erico.desafio.itau.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(description = "Dados de uma nova transação")
public interface TransactionRequestSpec {

    @Schema(description = "Valor da transação", example = "1200.69")
    BigDecimal value();

    @Schema(description = "Data e hora da realização da transação", example = "2025-05-20T03:09:26.808Z")
    OffsetDateTime dateTime();
}
