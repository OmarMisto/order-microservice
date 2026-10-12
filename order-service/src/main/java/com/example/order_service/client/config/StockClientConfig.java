package com.example.order_service.client.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class StockClientConfig {
    @Bean
    public ErrorDecoder productNotFoundErrorDecoder(){
        return new StockProductNotFoundErrorDecoder();
    }
    @Bean
    public ErrorDecoder stockOutOfQuantityErrorDecoder(){
        return new StockOutOfRequiredQuantityErrorDecoder();
    }
}
