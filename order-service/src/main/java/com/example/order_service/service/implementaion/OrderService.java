package com.example.order_service.service.implementaion;

import com.example.order_service.client.StockFeignClient;
import com.example.order_service.model.dto.GetStockProductDto;
import com.example.order_service.model.dto.ReduceProductDto;
import com.example.order_service.model.dto.OrderProductDto;
import com.example.order_service.model.dto.OrderProductResponseDto;
import com.example.order_service.service.IOrderService;
import com.example.order_service.service.exception.StockOutOfRequiredQuantityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final StockFeignClient stockFeignClient;
    @Override
    public OrderProductResponseDto orderService(OrderProductDto orderProductDto) {
        GetStockProductDto getStockProductDto=stockFeignClient.getStockProductByIdController(orderProductDto.getStockProductId());
        if(orderProductDto.getQuantity()>getStockProductDto.getQuantity()){
           throw new StockOutOfRequiredQuantityException("stock out of required quantity");
        }


        stockFeignClient.reduceStockProductController(ReduceProductDto.builder().productId(orderProductDto.getStockProductId()).quantity(orderProductDto.getQuantity()).build());

        return null;
    }
}
