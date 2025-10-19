package com.example.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderItem junction entity for many-to-many relationship between Order and Product.
 * Stores additional information like quantity and price at time of order.
 * This is an e-commerce best practice: products' prices change over time,
 * so we need to capture the price at the moment the order was placed.
 */
@Entity
@Data
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Quantity of this product in the order.
     */
    private Integer quantity;

    /**
     * Price of the product at the time of order.
     * Important: Product prices may change, but historical orders should reflect
     * the price that was valid when the order was placed.
     */
    private BigDecimal priceAtOrderTime;
}
