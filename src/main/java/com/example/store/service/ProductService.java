package com.example.store.service;

import com.example.store.entity.Product;
import com.example.store.exception.ResourceNotFoundException;
import com.example.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Product operations.
 * Provides transaction management and business logic separation.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Get all products with order items eagerly fetched.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get all products with pagination.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @param pageable pagination information (page, size, sort)
     * @return paginated list of products
     */
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Get product by ID with order items eagerly fetched.
     * Uses @EntityGraph in repository to prevent N+1 query problem.
     *
     * @param id the product ID
     * @return the product
     * @throws ResourceNotFoundException if product not found
     */
    public Product getProductById(Long id) {
        return productRepository.findWithOrderItemsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    /**
     * Create a new product.
     *
     * @param product the product to create
     * @return the saved product
     */
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
