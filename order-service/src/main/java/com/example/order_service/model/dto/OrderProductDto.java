package com.example.order_service.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderProductDto {
    private long stockProductId;
    private long userId;
    private int quantity;
    private String deleverLocation;
    private PaymentDto payment;
}
