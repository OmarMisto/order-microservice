package com.example.order_service.service.implementaion;

import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.ItemDto;
import com.example.order_service.model.dto.PaymentDuesEvent;
import com.example.order_service.model.dto.ReducedProductEvent;
import com.example.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KafkaStockReserveConsumer {
    private final IOrderService iOrderService;
    private final KafkaPaymentDuesService kafkaPaymentDuesService;
    @KafkaListener(topics = "stock-reserved", groupId = "stock-service-group")
    public void stockReserveListener(ReducedProductEvent reducedProductEvent){
        Order order= iOrderService.updateOrderStatusService(reducedProductEvent.getOrderId(), Optional.empty(),OrderStatus.READY_FOR_PAYMENT);
        kafkaPaymentDuesService.sendPaymentDues(PaymentDuesEvent.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .date(LocalDateTime.now())
                .items(order.getItems()
                        .stream()
                        .map(item -> ItemDto.builder().stockProductId(item.getProductId()).quantity(item.getQuantity()).unitPrice(item.getUnitPrice()).discountPercentage(item.getDiscountPercentage()).build()).toList())
                .build());
    }
}
