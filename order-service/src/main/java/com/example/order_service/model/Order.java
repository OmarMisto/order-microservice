package com.example.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_gen")
    @SequenceGenerator(name = "order_gen",sequenceName = "order_sec")
    private long orderId;
    @Column(nullable = false,unique = true)
    private long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String paymentStatus;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Item> items;
}
