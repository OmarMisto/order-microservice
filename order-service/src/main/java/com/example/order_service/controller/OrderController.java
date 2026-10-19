package com.example.order_service.controller;

import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.OrderProductResponseDto;
import com.example.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService iOrderService;
    @PostMapping("place/order")
    public ResponseEntity.HeadersBuilder<?> placeOrderController(@RequestBody OrderDto orderDto){
        iOrderService.orderService(orderDto);
        return ResponseEntity.noContent();
    }
    @GetMapping("get/{orderId}")
    public ResponseEntity<OrderProductResponseDto> getOrderController(@PathVariable("orderId") long orderId){
        return ResponseEntity.ok().body(iOrderService.getOrderById(orderId));
    }
}
