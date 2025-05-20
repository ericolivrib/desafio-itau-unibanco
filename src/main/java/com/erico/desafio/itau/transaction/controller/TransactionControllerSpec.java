package com.erico.desafio.itau.transaction.controller;

import com.erico.desafio.itau.transaction.dto.StatisticsResponse;
import com.erico.desafio.itau.transaction.dto.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Transaction", description = "Transações")
public interface TransactionControllerSpec {

    @Operation(summary = "Criar transação", description = "Adiciona uma transação na fila de transações", requestBody = @RequestBody(description = "Transação a ser criada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionRequest.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "JSON de transação malformado"),
            @ApiResponse(responseCode = "422", description = "Campos informados não atendem os requisitos de transações"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Void> createTransaction(TransactionRequest request, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Deletar transações", description = "Limpa todas as transações da fila de transações", responses = {
            @ApiResponse(responseCode = "200", description = "Transações deletadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Void> deleteTransactions();

    @Operation(summary = "Estatísticas de transações", description = "Recupera as estatísticas das transações adicionadas em um intervalo de tempo", parameters = @Parameter(name = "intervalo", description = "Últimos segundos em que transações foram adicionadas", example = "60"), responses = {
            @ApiResponse(responseCode = "200", description = "Estatísticas recuperadas com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StatisticsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    ResponseEntity<StatisticsResponse> getStatistics(int interval);
}
