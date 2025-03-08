package com.erico.desafio.itau.controller;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.UUID;

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
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transacao")
  public ResponseEntity<Void> createTransaction(@RequestBody @Valid TransactionRequest request, UriComponentsBuilder uriBuilder) {
    if (request.dateTime().isAfter(OffsetDateTime.now())) {
      return ResponseEntity.unprocessableEntity().build();
    }

    UUID transactionId = this.transactionService.addTransaction(request.toModel());
    URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transactionId).toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("/transacao")
  public ResponseEntity<Void> deleteTransactions() {
    transactionService.clearTransactions();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/estatistica")
  public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam(name = "intervalo", defaultValue = "60", required = false) int interval) {
    DoubleSummaryStatistics statistics = transactionService.getStatistics(interval);

    if (statistics.getCount() == 0) {
      return ResponseEntity.ok(new StatisticsResponse(0,0,0,0,0));
    }

    return ResponseEntity.ok(new StatisticsResponse(statistics));
  }

}
