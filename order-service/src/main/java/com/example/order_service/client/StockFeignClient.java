package com.example.order_service.client;

import com.example.order_service.client.config.StockClientConfig;
import com.example.order_service.model.dto.GetStockProductDto;
import com.example.order_service.model.dto.ReduceProductDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock-service",url = "api/vi/stock/",
        configuration = StockClientConfig.class,
        fallback = StockFeignClientFallback.class)
public interface StockFeignClient {
    @GetMapping("get/{stockProductId}")
    public GetStockProductDto getStockProductByIdController(@PathVariable(name = "stockProductId") long stockProductId);
    @PatchMapping("reduce/product")
    public void reduceStockProductController(@RequestBody ReduceProductDto reduceProductDto);
}
