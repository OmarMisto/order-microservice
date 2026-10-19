package com.example.order_service.service.implementaion;
import com.example.order_service.model.Item;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.ItemDto;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.OrderProductResponseDto;
import com.example.order_service.model.dto.OrderEvent;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.IOrderService;
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
    public void updateOrderStatusService(long orderId, OrderStatus orderStatus) {
        Order order =orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException(" order not found"));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
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
