package com.example.order_service.model.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ItemDto {
    private long stockProductId;
    private int quantity;
    private BigDecimal unitPrice;
    private double discountPercentage;
}
