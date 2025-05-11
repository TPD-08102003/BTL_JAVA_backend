package org.example.btl_java.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//  @ExceptionHandler(OrderNotFoundException.class)
//  public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException ex) {
//    logger.error("Order not found: {}", ex.getMessage());
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//  }
//
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleGeneralException(Exception ex) {
//    logger.error("Unexpected error: {}", ex.getMessage(), ex);
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + ex.getMessage());
//  }
}