package com.example.order_service.service.exception;

public class StockServiceUnAvailableException extends RuntimeException {
    public StockServiceUnAvailableException(String message) {
        super(message);
    }
}
