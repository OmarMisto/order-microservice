package com.example.order_service.service;

import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.OrderProductResponseDto;

public interface IOrderService {
    public void orderService(OrderDto orderDto);
    public void updateOrderStatusService(long orderId, OrderStatus orderStatus);
    public OrderProductResponseDto getOrderById(long orderId);
}
