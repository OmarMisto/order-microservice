package com.example.order_service.service.implementaion;

import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.HandlePaymentEvent;
import com.example.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KafkaHandlePaymentConsumer {
    private final IOrderService iOrderService;
   @KafkaListener(topics = "handle-payment",groupId = "order-service-group")
    public void handlePaymentListener(HandlePaymentEvent handlePaymentEvent){
       if (handlePaymentEvent.getPaymentStatus().equalsIgnoreCase("success")){
           iOrderService.updateOrderStatusService(handlePaymentEvent.getOrderId(),Optional.empty(), OrderStatus.SUCCESS);
       }
       iOrderService.updateOrderStatusService(handlePaymentEvent.getOrderId(), Optional.of("payment failed"),OrderStatus.FAILED);
   }
}
