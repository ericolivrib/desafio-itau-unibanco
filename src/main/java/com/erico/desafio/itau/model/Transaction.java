package com.erico.desafio.itau.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transaction {

  private UUID id;
  private BigDecimal value;
  private OffsetDateTime dateTime;

  public Transaction(UUID id, BigDecimal value, OffsetDateTime dateTime) {
    this.id = id;
    this.value = value;
    this.dateTime = dateTime;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
  
  public BigDecimal getValue() {
    return value;
  }
  
  public void setValue(BigDecimal value) {
    this.value = value;
  }
  
  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }
}