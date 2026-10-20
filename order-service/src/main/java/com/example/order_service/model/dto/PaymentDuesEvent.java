package com.example.order_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PaymentDuesEvent {
    private long orderId;
    private long userId;
    private LocalDateTime date;
    private List<ItemDto> items;
}
