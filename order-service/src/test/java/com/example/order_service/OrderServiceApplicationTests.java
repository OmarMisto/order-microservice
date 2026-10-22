package com.example.order_service;

import com.example.order_service.model.Item;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.model.dto.ItemDto;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.OrderEvent;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.exception.OrderFailedException;
import com.example.order_service.service.implementaion.KafkaProducerService;
import com.example.order_service.service.implementaion.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderServiceApplicationTests {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private KafkaProducerService kafkaProducerService;
	@InjectMocks
    private OrderService orderService;
	@Test
    public void  testTheSuccessOfOrderServiceWithValidData(){
		ItemDto itemDto = ItemDto.builder()
				.stockProductId(10L)
				.unitPrice(BigDecimal.valueOf(50))
				.quantity(2)
				.discountPercentage(0.10)
				.build();

		OrderDto orderDto = OrderDto.builder()
				.userId(1L)
				.items(List.of(itemDto))
				.build();

		Order savedOrder = Order.builder()
				.orderId(100L)
				.userId(1L)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.PENDING)
				.items(orderDto.getItems().stream().map(item->Item.builder().itemId(item.getStockProductId()+1)
						.productId(item.getStockProductId())
						.unitPrice(item.getUnitPrice()).quantity(item.getQuantity())
						.discountPercentage(item.getDiscountPercentage()).build()).toList())
				.build();

		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(savedOrder);

		orderService.orderService(orderDto);
		ArgumentCaptor<OrderEvent> eventCaptor = ArgumentCaptor.forClass(OrderEvent.class);
		Mockito.verify(kafkaProducerService).sendOrder(eventCaptor.capture());

		OrderEvent capturedEvent = eventCaptor.getValue();

		assertEquals(100L, capturedEvent.getOrderId());
		assertEquals("order_created", capturedEvent.getEvent());
		assertEquals(List.of(itemDto), capturedEvent.getItems());

		Mockito.verify(orderRepository).save(Mockito.any(Order.class));
	}
	@Test
	public void testUpdateOrderServiceWithReadyForPaymentOrderStatus(){
		long mockOrderId=101;
		Optional<String> mockMessage=Optional.empty();
		OrderStatus mockOrderStatus=OrderStatus.READY_FOR_PAYMENT;
		Item item=Item.builder()
				.itemId(111)
				.productId(987)
				.unitPrice(BigDecimal.valueOf(49.99))
				.quantity(2)
				.discountPercentage(0.2)
				.build();
		Order mockOrder=Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.PENDING)
				.items(List.of(item)).build();
		Mockito.when(orderRepository.findById(mockOrderId)).thenReturn(Optional.of(mockOrder));
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.READY_FOR_PAYMENT)
				.items(List.of(item))
				.build());
		Order savedOrder= orderService.updateOrderStatusService(mockOrderId,mockMessage,mockOrderStatus);
		assertEquals(savedOrder.getOrderStatus().name(),OrderStatus.READY_FOR_PAYMENT.name());
		Mockito.verify(orderRepository,Mockito.atMost(1)).save(Mockito.any(Order.class));


	}
	@Test
	public void testUpdateOrderServiceWithFailedOrderStatus(){
		long mockOrderId=101;
		Optional<String> mockMessage=Optional.of("product out for stock for 111");
		OrderStatus mockOrderStatus=OrderStatus.FAILED;
		Item item=Item.builder()
				.itemId(111)
				.productId(987)
				.unitPrice(BigDecimal.valueOf(49.99))
				.quantity(2)
				.discountPercentage(0.2)
				.build();
		Order mockOrder=Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.PENDING)
				.items(List.of(item)).build();
		Mockito.when(orderRepository.findById(mockOrderId)).thenReturn(Optional.of(mockOrder));
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.FAILED)
				.items(List.of(item))
				.build());

		OrderFailedException orderFailedException=assertThrows(OrderFailedException.class,()->{
			orderService.updateOrderStatusService(mockOrderId,mockMessage,mockOrderStatus);
		});
		assertEquals("product out for stock for 111",orderFailedException.getMessage());
		ArgumentCaptor<Order> orderArgumentCaptor=ArgumentCaptor.forClass(Order.class);
		Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());

		Order savedOrder=orderArgumentCaptor.getValue();
		assertEquals(mockOrderStatus.name(),savedOrder.getOrderStatus().name());
		Mockito.verify(orderRepository,Mockito.atMost(1)).save(Mockito.any(Order.class));

	}
	@Test
	public void testUpdateOrderServiceWithSuccessOrderStatus(){
		long mockOrderId=101;
		Optional<String> mockMessage=Optional.empty();
		OrderStatus mockOrderStatus=OrderStatus.SUCCESS;
		Item item=Item.builder()
				.itemId(111)
				.productId(987)
				.unitPrice(BigDecimal.valueOf(49.99))
				.quantity(2)
				.discountPercentage(0.2)
				.build();
		Order mockOrder=Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.READY_FOR_PAYMENT)
				.items(List.of(item)).build();
		Mockito.when(orderRepository.findById(mockOrderId)).thenReturn(Optional.of(mockOrder));
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(Order.builder()
				.orderId(101)
				.userId(220)
				.orderDate(LocalDateTime.now())
				.orderStatus(OrderStatus.SUCCESS)
				.items(List.of(item))
				.build());
		Order savedOrder= orderService.updateOrderStatusService(mockOrderId,mockMessage,mockOrderStatus);

		assertEquals(mockOrderStatus.name(),savedOrder.getOrderStatus().name());
		Mockito.verify(orderRepository,Mockito.atMost(1)).save(Mockito.any(Order.class));

	}


}
