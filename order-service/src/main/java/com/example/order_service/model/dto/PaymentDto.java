package com.example.order_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PaymentDto {
    private String cardNo;
    private LocalDateTime expireDate;
}
