package com.example.store.service;

import com.example.store.entity.Product;
import com.example.store.exception.ResourceNotFoundException;
import com.example.store.repository.ProductRepository;

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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setDescription("Football");

        product2 = new Product();
        product2.setId(2L);
        product2.setDescription("Basketball");
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(product1, product2);
        verify(productRepository).findAll();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findWithOrderItemsById(1L)).thenReturn(Optional.of(product1));

        Product result = productService.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescription()).isEqualTo("Football");
        verify(productRepository).findWithOrderItemsById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findWithOrderItemsById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product")
                .hasMessageContaining("999");

        verify(productRepository).findWithOrderItemsById(999L);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(product1)).thenReturn(product1);

        Product result = productService.createProduct(product1);

        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Football");
        assertThat(result.getId()).isEqualTo(1L);
        verify(productRepository).save(product1);
    }
}
