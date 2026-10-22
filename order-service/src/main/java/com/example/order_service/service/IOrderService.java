package com.example.order_service.service;

import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.OrderProductResponseDto;

import java.util.Optional;

public interface IOrderService {
    public void orderService(OrderDto orderDto);
    public Order updateOrderStatusService(long orderId, Optional <String> message , OrderStatus orderStatus);
    public OrderProductResponseDto getOrderById(long orderId);
}
