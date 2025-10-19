package com.example.store.controller;

import com.example.store.dto.CreateProductRequest;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** REST controller for Product operations. Provides endpoints for creating and retrieving products. */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    /**
     * Get all products with pagination.
     *
     * @param page page number (default: 0)
     * @param size page size (default: 20)
     * @param sort sort criteria (default: id,desc)
     * @return paginated list of products
     */
    @GetMapping
    public Page<ProductDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        Sort.Direction direction =
                sort.length > 1 && sort[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        Page<Product> productPage = productService.getAllProductsPaginated(pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    /**
     * Get product by ID.
     *
     * @param id the product ID
     * @return the product with order IDs
     */
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productMapper.productToProductDTO(productService.getProductById(id));
    }

    /**
     * Create a new product.
     *
     * @param request the product creation request
     * @return the created product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody CreateProductRequest request) {
        Product product = productMapper.createProductRequestToProduct(request);
        return productMapper.productToProductDTO(productService.createProduct(product));
    }
}
