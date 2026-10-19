package com.example.order_service.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ReducedProductEvent {
    private long orderId;
    private List<ItemDto> items;
    private LocalDateTime date;
    private String event;
}
