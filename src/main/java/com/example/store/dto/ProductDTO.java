package com.example.store.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO for Product entity.
 * Includes list of order IDs that contain this product.
 */
@Data
public class ProductDTO {
    private Long id;
    private String description;
    private List<Long> orderIds;
}
