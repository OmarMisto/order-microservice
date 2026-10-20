package com.example.order_service.service.exception;

public class OrderFailedException extends RuntimeException {
    public OrderFailedException(String message) {
        super(message);
    }
}
