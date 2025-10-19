package com.example.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Product entity representing items that can be ordered. Uses many-to-many relationship with Order through OrderItem
 * junction table.
 *
 * <p>Note: Replaced @Data with @Getter/@Setter and custom equals/hashCode/toString. See Customer entity for detailed
 * discussion on why this is debatable.
 */
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", description='" + description + '\'' + '}';
    }
}
