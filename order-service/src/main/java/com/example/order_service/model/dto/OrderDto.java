package com.example.order_service.model.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderDto {
    private long userId;
    private List<ItemDto> items;

}
