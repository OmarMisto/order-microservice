package com.example.order_service.service.exception;

public class StockOutOfRequiredQuantityException extends RuntimeException {
    public StockOutOfRequiredQuantityException(String message) {
        super(message);
    }
}
