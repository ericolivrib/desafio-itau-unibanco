package com.erico.desafio.itau.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Transaction {

  private UUID id;
  private Double value;
  private OffsetDateTime dateTime;

  public Transaction(UUID id, Double value, OffsetDateTime dateTime) {
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
  
  public Double getValue() {
    return value;
  }
  
  public void setValue(Double value) {
    this.value = value;
  }
  
  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }
}