package com.example.order_service.service;

import com.example.order_service.model.dto.OrderProductDto;
import com.example.order_service.model.dto.OrderProductResponseDto;

public interface IOrderService {
    public OrderProductResponseDto orderService(OrderProductDto orderProductDto);
}
