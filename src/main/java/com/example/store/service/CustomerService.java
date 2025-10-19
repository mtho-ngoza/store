package com.example.store.service;

import com.example.store.entity.Customer;
import com.example.store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Customer operations.
 * Provides transaction management and business logic separation.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Get all customers with orders eagerly fetched.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @return list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Get all customers with pagination.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @param pageable pagination information (page, size, sort)
     * @return paginated list of customers
     */
    public Page<Customer> getAllCustomersPaginated(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    /**
     * Search customers by name substring with pagination.
     * Case-insensitive search that matches any word in the customer name.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @param name     the substring to search for in customer names
     * @param pageable pagination information (page, size, sort)
     * @return paginated list of matching customers
     */
    public Page<Customer> searchCustomersByName(String name, Pageable pageable) {
        return customerRepository.searchByName(name, pageable);
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer to create
     * @return the saved customer
     */
    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
