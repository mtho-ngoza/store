package com.example.store.repository;

import com.example.store.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Product entity. Uses @EntityGraph to prevent N+1 query problem when fetching associated OrderItems.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find all products with order items eagerly fetched. Prevents N+1 query: instead of 1 + N queries, executes only 1
     * query with JOIN.
     *
     * @return list of all products with order items
     */
    @EntityGraph(attributePaths = {"orderItems"})
    List<Product> findAll();

    /**
     * Find all products with pagination and order items eagerly fetched. Prevents N+1 query: instead of 1 + N queries,
     * executes only 1 query with JOIN.
     *
     * @param pageable pagination information
     * @return paginated list of products with order items
     */
    @EntityGraph(attributePaths = {"orderItems"})
    Page<Product> findAll(Pageable pageable);

    /**
     * Find product by ID with order items eagerly fetched. Prevents N+1 query when accessing product.getOrderItems().
     *
     * @param id the product ID
     * @return optional containing the product if found
     */
    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Product> findWithOrderItemsById(Long id);
}
