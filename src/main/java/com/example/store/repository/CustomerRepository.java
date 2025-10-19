package com.example.store.repository;

import com.example.store.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Customer entity.
 * Uses @EntityGraph to prevent N+1 query problem when fetching associated Orders.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find all customers with orders eagerly fetched.
     * Prevents N+1 query: instead of 1 + N queries, executes only 1 query with JOIN.
     *
     * @return list of all customers with orders
     */
    @EntityGraph(attributePaths = {"orders"})
    List<Customer> findAll();

    /**
     * Find all customers with pagination and orders eagerly fetched.
     * Prevents N+1 query: instead of 1 + N queries, executes only 1 query with JOIN.
     *
     * @param pageable pagination information
     * @return paginated list of customers with orders
     */
    @EntityGraph(attributePaths = {"orders"})
    Page<Customer> findAll(Pageable pageable);

    /**
     * Find customer by ID with orders eagerly fetched.
     * Prevents N+1 query when accessing customer.getOrders().
     *
     * @param id the customer ID
     * @return optional containing the customer if found
     */
    @EntityGraph(attributePaths = {"orders"})
    Optional<Customer> findById(Long id);

    /**
     * Search customers by name substring (case-insensitive) with pagination.
     * Matches any word in the customer name.
     * Uses @EntityGraph to prevent N+1 query when fetching associated orders.
     *
     * @param searchTerm the substring to search for in customer names
     * @param pageable   pagination information (page, size, sort)
     * @return paginated list of matching customers with orders
     */
    @EntityGraph(attributePaths = {"orders"})
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Customer> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);
}
