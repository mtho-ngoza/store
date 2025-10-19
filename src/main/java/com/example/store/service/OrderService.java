package com.example.store.service;

import com.example.store.entity.Order;
import com.example.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Order operations.
 * Provides transaction management and business logic separation.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Create a new order.
     *
     * @param order the order to create
     * @return the saved order
     */
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
