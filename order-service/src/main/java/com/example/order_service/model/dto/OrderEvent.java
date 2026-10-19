package com.example.order_service.model.dto;

import lombok.*;

import java.awt.event.ItemEvent;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderEvent {
    private long orderId;
    private List<ItemDto> items;
    private String event;
    private LocalDateTime date;
}
