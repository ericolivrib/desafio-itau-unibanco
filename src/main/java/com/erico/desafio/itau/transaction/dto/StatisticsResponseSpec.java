package com.erico.desafio.itau.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estatísticas das transações efetuadas em um determinado intervalo de tempo")
public interface StatisticsResponseSpec {
    @Schema(description = "Quantidade de transações", example = "10")
    long count();

    @Schema(description = "Valor acumulado das transações", example = "1500")
    double sum();

    @Schema(description = "Valor médio das transações", example = "150")
    double avg();

    @Schema(description = "Menor valor de uma transação", example = "100")
    double min();

    @Schema(description = "Maior valor de uma transação", example = "500")
    double max();
}
