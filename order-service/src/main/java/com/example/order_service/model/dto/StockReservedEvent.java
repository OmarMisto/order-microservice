package com.example.order_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StockReservedEvent {
    private long orderId;
    private String event;
    private LocalDateTime date;
    private String message;
}
