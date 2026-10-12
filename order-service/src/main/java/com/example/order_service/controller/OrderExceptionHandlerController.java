package com.example.order_service.controller;

import com.example.order_service.model.dto.ErrorMessageResponse;
import com.example.order_service.service.exception.ProductNotFoundException;
import com.example.order_service.service.exception.StockOutOfRequiredQuantityException;
import com.example.order_service.service.exception.StockServiceUnAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandlerController {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> productNotFoundExceptionHandler(ProductNotFoundException e){
        return ResponseEntity.status(404).body(ErrorMessageResponse.builder()
                .status(404)
                .Error("NOT_FOUND")
                .message(e.getMessage())
                .build());
    }
    @ExceptionHandler(StockOutOfRequiredQuantityException.class)
    public ResponseEntity<ErrorMessageResponse> stockOutOfRequiredQuantityExceptionHandler(StockOutOfRequiredQuantityException e){
        return ResponseEntity.status(400).body(ErrorMessageResponse.builder()
                .status(400)
                .Error("BAD_REQUEST")
                .message(e.getMessage())
                .build());
    }
    @ExceptionHandler(StockServiceUnAvailableException.class)
    public ResponseEntity<ErrorMessageResponse> stockServiceUnavailableExceptionHandler(StockServiceUnAvailableException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorMessageResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .Error("SERVICE_UNAVAILABLE")
                .message(e.getMessage())
                .build());
    }

}
