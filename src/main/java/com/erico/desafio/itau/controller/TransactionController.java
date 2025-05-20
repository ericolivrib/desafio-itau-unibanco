package com.erico.desafio.itau.controller;

import java.time.OffsetDateTime;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TransactionController implements TransactionControllerSpec {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transacao")
  public ResponseEntity<Void> createTransaction(@RequestBody @Valid TransactionRequest request, UriComponentsBuilder uriBuilder) {
    UUID transactionId = this.transactionService.addTransaction(request);

    String uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transactionId).toUriString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header(HttpHeaders.LOCATION, uri)
        .build();
  }

  @DeleteMapping("/transacao")
  public ResponseEntity<Void> deleteTransactions() {
    transactionService.clearTransactions();
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @GetMapping("/estatistica")
  public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam(name = "intervalo", defaultValue = "60", required = false) int interval) {
    StatisticsResponse response = transactionService.getStatistics(interval);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

}
