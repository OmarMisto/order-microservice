package com.example.order_service.service.implementaion;

import com.example.order_service.model.dto.PaymentDuesEvent;
import lombok.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPaymentDuesService {
    private final String topic="payment-dues";
    private final KafkaTemplate<String, PaymentDuesEvent> kafkaTemplate;
    public void sendPaymentDues(PaymentDuesEvent paymentDuesEvent){
        kafkaTemplate.send(topic,paymentDuesEvent.getOrderId()+"",paymentDuesEvent);
    }
}
