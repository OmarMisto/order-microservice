package com.example.order_service.service.implementaion;
import com.example.order_service.model.Item;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.*;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.IOrderService;
import com.example.order_service.service.exception.OrderFailedException;
import com.example.order_service.service.exception.OrderNotFoundException;
import com.example.order_service.service.exception.UpStreamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final  KafkaProducerService kafkaProducerService;
    private final KafkaPaymentDuesService kafkaPaymentDuesService;
    @Override
    @Transactional
    public void orderService(OrderDto orderDto) {
        Order savedOrder= orderRepository.save(Order.builder()
                .userId(orderDto.getUserId())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .items(orderDto.getItems().stream()
                        .map(itemDto -> Item.builder()
                                .productId(itemDto.getStockProductId())
                                .unitPrice(itemDto.getUnitPrice())
                                .quantity(itemDto.getQuantity())
                                .discountPercentage(itemDto.getDiscountPercentage())

                                .build())
                        .toList())
                .build());
        try {
            kafkaProducerService.sendOrder(OrderEvent.builder()
                    .orderId(savedOrder.getOrderId())
                    .items(orderDto.getItems())
                    .event("order_created")
                    .date(LocalDateTime.now())
                    .build());
        }catch (Exception e){
            throw new UpStreamException("un able to connect to message broker");
        }
    }
    @Override
    @Transactional
    public void updateOrderStatusService(long orderId,String message ,OrderStatus orderStatus) {
        Order order =orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException(" order not found"));
        if (orderStatus.name().equalsIgnoreCase("failed")){
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            throw new OrderFailedException(message);
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        kafkaPaymentDuesService.sendPaymentDues(PaymentDuesEvent.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .items(order.getItems()
                        .stream()
                        .map(item -> ItemDto.builder().stockProductId(item.getProductId()).quantity(item.getQuantity()).unitPrice(item.getUnitPrice()).discountPercentage(item.getDiscountPercentage()).build()).toList())
                .build());
    }

    @Override
    @Transactional
    public OrderProductResponseDto getOrderById(long orderId) {
        Order order =orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("order not found"));
        return OrderProductResponseDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus().name())
                .items(order.getItems().stream().map(item -> ItemDto.builder()
                        .stockProductId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discountPercentage(item.getDiscountPercentage())
                        .build()).toList())
                .date(order.getOrderDate())
                .build();
    }
}
