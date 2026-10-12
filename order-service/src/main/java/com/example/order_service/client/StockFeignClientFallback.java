package com.example.order_service.client;

import com.example.order_service.model.dto.GetStockProductDto;
import com.example.order_service.model.dto.ReduceProductDto;
import com.example.order_service.service.exception.StockServiceUnAvailableException;
import org.springframework.stereotype.Component;

@Component
public class StockFeignClientFallback implements StockFeignClient {
    @Override
    public GetStockProductDto getStockProductByIdController(long stockProductId) {
        throw new StockServiceUnAvailableException("stock service is currently unavailable");
    }

    @Override
    public void reduceStockProductController(ReduceProductDto reduceProductDto) {
        throw new StockServiceUnAvailableException("stock service is currently unavailable");
    }
}
