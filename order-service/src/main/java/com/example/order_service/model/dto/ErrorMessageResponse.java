package com.example.order_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorMessageResponse {
    private int status;
    private String Error;
    private String message;
    private final LocalDateTime date=LocalDateTime.now();
}
