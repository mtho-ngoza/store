package com.example.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * OrderItem junction entity for many-to-many relationship between Order and Product. Stores additional information like
 * quantity and price at time of order. This is an e-commerce best practice: products' prices change over time, so we
 * need to capture the price at the moment the order was placed.
 *
 * <p>Note: Replaced @Data with @Getter/@Setter and custom equals/hashCode/toString. See Customer entity for detailed
 * discussion on why this is debatable.
 */
@Entity
@Getter
@Setter
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

    /** Quantity of this product in the order. */
    private Integer quantity;

    /**
     * Price of the product at the time of order. Important: Product prices may change, but historical orders should
     * reflect the price that was valid when the order was placed.
     */
    private BigDecimal priceAtOrderTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return id != null && Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "OrderItem{" + "id=" + id + ", quantity=" + quantity + ", priceAtOrderTime=" + priceAtOrderTime + '}';
    }
}
