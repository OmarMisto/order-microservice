package com.example.order_service.controller;

import com.example.order_service.model.dto.ErrorMessageResponse;
import com.example.order_service.service.exception.OrderNotFoundException;
import com.example.order_service.service.exception.UpStreamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandlerController {
    @ExceptionHandler(UpStreamException.class)
    public ResponseEntity<ErrorMessageResponse> upStreamExceptionHandler(UpStreamException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorMessageResponse.builder()
                        .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .Error("SERVICE_UNAVAILABLE")
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse>orderNotFoundExceptionHandler(OrderNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessageResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .Error("NOT_FOUND")
                        .message(e.getMessage())
                        .build());
    }
}
