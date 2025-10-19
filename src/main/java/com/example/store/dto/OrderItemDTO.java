package com.example.store.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for OrderItem - represents a product within an order.
 * Includes product details, quantity, and price at time of order.
 */
@Data
public class OrderItemDTO {
    private Long productId;
    private String productDescription;
    private Integer quantity;
    private BigDecimal priceAtOrderTime;
}
