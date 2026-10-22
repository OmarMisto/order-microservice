package com.example.order_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HandlePaymentEvent {
    private long orderId;
    private String paymentStatus;
    private String event;
    private LocalDateTime date;
}
