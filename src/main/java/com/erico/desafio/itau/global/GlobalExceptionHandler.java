package com.erico.desafio.itau.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Void> handleUnprocessableEntity() {
    return ResponseEntity
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .build();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Void> handleBadRequest() {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleInternalServerError() {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .build();
  }

}
