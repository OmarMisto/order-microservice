package com.example.order_service.model.dto;

import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GetStockProductDto {
    private long stockProductId;
    private String productNo;
    private String brand;
    private int quantity;
    private BigDecimal unitPrice;
    private int discountPercentage;
    private boolean isAvailable;
}
