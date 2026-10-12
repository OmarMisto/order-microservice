package com.example.order_service.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ReduceProductDto {
    private long productId;
    private int quantity;
}
