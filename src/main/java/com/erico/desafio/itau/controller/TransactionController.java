package com.erico.desafio.itau.controller;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.erico.desafio.itau.dto.StatisticsResponse;
import com.erico.desafio.itau.dto.TransactionRequest;
import com.erico.desafio.itau.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transacao")
  @Operation(summary = "Criar transação", description = "Adiciona uma transação na fila de transações", responses = {
    @ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso"),
    @ApiResponse(responseCode = "400", description = "JSON de transação malformado"),
    @ApiResponse(responseCode = "422", description = "Campos informados não atendem os requisitos de transações"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public ResponseEntity<Void> createTransaction(@RequestBody @Valid TransactionRequest request, UriComponentsBuilder uriBuilder) {
    if (request.dateTime().isAfter(OffsetDateTime.now())) {
      return ResponseEntity
          .status(HttpStatus.UNPROCESSABLE_ENTITY)
          .build();
    }

    UUID transactionId = this.transactionService.addTransaction(request.toModel());
    String uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transactionId).toUriString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header(HttpHeaders.LOCATION, uri)
        .build();
  }

  @DeleteMapping("/transacao")
  @Operation(summary = "Deletar transações", description = "Limpa todas as transações da fila de transações", responses = {
    @ApiResponse(responseCode = "200", description = "Transações deletadas com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public ResponseEntity<Void> deleteTransactions() {
    transactionService.clearTransactions();
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @GetMapping("/estatistica")
  @Operation(summary = "Estatísticas de transações", description = "Recupera as estatísticas das transações adicionadas em um intervalo de tempo", parameters = @Parameter(name = "intervalo", description = "Últimos segundos em que transações foram adicionadas", required = false, example = "60"), responses = {
    @ApiResponse(responseCode = "200", description = "Estatísticas recuperadas com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam(name = "intervalo", defaultValue = "60", required = false) int interval) {
    DoubleSummaryStatistics statistics = transactionService.getStatistics(interval);

    if (statistics.getCount() == 0) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new StatisticsResponse(0, 0, 0, 0, 0));
    }

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new StatisticsResponse(statistics));
  }

}
