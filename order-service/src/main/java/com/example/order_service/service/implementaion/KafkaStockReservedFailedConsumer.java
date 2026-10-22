package com.example.order_service.service.implementaion;

import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.StockReservedEvent;
import com.example.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KafkaStockReservedFailedConsumer {
    private final IOrderService iOrderService;
    @KafkaListener(topics = "stock-reserved-failed",groupId = "stock-group")
    public void stockReservedListener(StockReservedEvent stockReservedEvent){
        iOrderService.updateOrderStatusService(stockReservedEvent.getOrderId(), Optional.of(stockReservedEvent.getMessage()), OrderStatus.FAILED);
    }
}
