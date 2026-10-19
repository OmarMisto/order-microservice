package com.example.order_service.model.dto;

import com.example.order_service.model.Item;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderProductResponseDto {
    private long orderId;
    private List<ItemDto> items;

    private LocalDateTime date;
}
