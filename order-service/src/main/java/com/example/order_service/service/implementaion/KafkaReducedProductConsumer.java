package com.example.order_service.service.implementaion;

import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.ReducedProductEvent;
import com.example.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaReducedProductConsumer {
    private final IOrderService iOrderService;
    @KafkaListener(topics = "stock-reserved",groupId = "stock-service-group")
    public void reduceProductListener(ReducedProductEvent reducedProductEvent){
         iOrderService.updateOrderStatusService(reducedProductEvent.getOrderId(), OrderStatus.READY_FOR_PAYMENT);
    }
}
