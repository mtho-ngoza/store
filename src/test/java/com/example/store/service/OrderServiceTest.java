package com.example.store.service;

import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.exception.ResourceNotFoundException;
import com.example.store.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        order1 = new Order();
        order1.setId(1L);
        order1.setDescription("Order 1");
        order1.setCustomer(customer);

        order2 = new Order();
        order2.setId(2L);
        order2.setDescription("Order 2");
        order2.setCustomer(customer);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(order1, order2);
        verify(orderRepository).findAll();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findWithCustomerById(1L)).thenReturn(Optional.of(order1));

        Order result = orderService.getOrderById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescription()).isEqualTo("Order 1");
        assertThat(result.getCustomer().getName()).isEqualTo("John Doe");
        verify(orderRepository).findWithCustomerById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findWithCustomerById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order")
                .hasMessageContaining("999");

        verify(orderRepository).findWithCustomerById(999L);
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(order1)).thenReturn(order1);

        Order result = orderService.createOrder(order1);

        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Order 1");
        assertThat(result.getId()).isEqualTo(1L);
        verify(orderRepository).save(order1);
    }
}
