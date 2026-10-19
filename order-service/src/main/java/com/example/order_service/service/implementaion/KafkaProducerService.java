package com.example.order_service.service.implementaion;

import com.example.order_service.model.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final String topic="order-created";
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    public void sendOrder(OrderEvent orderEvent){
        kafkaTemplate.send(topic,""+orderEvent.getOrderId(),orderEvent);
    }
}
