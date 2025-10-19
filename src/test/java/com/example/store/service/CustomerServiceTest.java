package com.example.store.service;

import com.example.store.entity.Customer;
import com.example.store.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");

        pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(customer1, customer2);
        verify(customerRepository).findAll();
    }

    @Test
    void testGetAllCustomersPaginated() {
        Page<Customer> customerPage = new PageImpl<>(Arrays.asList(customer1, customer2));
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);

        Page<Customer> result = customerService.getAllCustomersPaginated(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactly(customer1, customer2);
        verify(customerRepository).findAll(pageable);
    }

    @Test
    void testSearchCustomersByName() {
        Page<Customer> customerPage = new PageImpl<>(List.of(customer1));
        when(customerRepository.searchByName("John", pageable)).thenReturn(customerPage);

        Page<Customer> result = customerService.searchCustomersByName("John", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
        verify(customerRepository).searchByName("John", pageable);
    }

    @Test
    void testSearchCustomersByNameCaseInsensitive() {
        Page<Customer> customerPage = new PageImpl<>(List.of(customer1));
        when(customerRepository.searchByName("john", pageable)).thenReturn(customerPage);

        Page<Customer> result = customerService.searchCustomersByName("john", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
        verify(customerRepository).searchByName("john", pageable);
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(customer1)).thenReturn(customer1);

        Customer result = customerService.createCustomer(customer1);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getId()).isEqualTo(1L);
        verify(customerRepository).save(customer1);
    }
}
