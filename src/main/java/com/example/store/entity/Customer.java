package com.example.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Customer entity representing a customer in the store.
 *
 * <p>Note: Using @Getter/@Setter instead of @Data to avoid Lombok-generated equals/hashCode/toString issues with JPA
 * entities. This is a debatable choice - @Data works fine for simple cases but can cause: 1. Mutable equals/hashCode
 * breaking collections when entity state changes 2. Circular toString with bidirectional relationships
 * (LazyInitializationException) 3. equals/hashCode on all fields instead of just ID (JPA best practice is ID-based
 * equality)
 *
 * <p>Alternative view: @Data is acceptable if you're careful about when entities are compared and ensure bidirectional
 * relationships use @ToString.Exclude. The choice depends on team standards.
 */
@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    /**
     * Equals based only on ID, following JPA best practices. Two customers are equal if they have the same ID (or both
     * are new entities with null ID).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return id != null && Objects.equals(id, customer.id);
    }

    /**
     * HashCode based only on ID for consistency with equals. Uses a constant to ensure hash code doesn't change when ID
     * is assigned.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * ToString excludes orders collection to prevent circular references and LazyInitializationException.
     */
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
