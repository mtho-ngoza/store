package com.example.store.repository;

import com.example.store.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Order entity.
 * Uses @EntityGraph to prevent N+1 query problem when fetching associated Customer.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders with customer and order items (with products) eagerly fetched.
     * Prevents N+1 query: instead of 1 + N queries, executes only 1 query with JOINs.
     *
     * @return list of all orders with customers and products
     */
    @EntityGraph(attributePaths = {"customer", "orderItems", "orderItems.product"})
    List<Order> findAll();

    /**
     * Find order by ID with customer and order items (with products) eagerly fetched.
     * Prevents N+1 query when accessing order.getCustomer() and order.getOrderItems().
     * Note: Using custom method name because @EntityGraph on overridden findById
     * may not work reliably across all Spring Data JPA versions.
     *
     * @param id the order ID
     * @return optional containing the order if found
     */
    @EntityGraph(attributePaths = {"customer", "orderItems", "orderItems.product"})
    Optional<Order> findWithCustomerById(Long id);
}
