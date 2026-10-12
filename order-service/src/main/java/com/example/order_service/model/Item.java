package com.example.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "item_gen")
    @SequenceGenerator(name = "item_gen",sequenceName = "item_sec")
    private long itemId;
    private long productId;
    private BigDecimal unitPrice;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
