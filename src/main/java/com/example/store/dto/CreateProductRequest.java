package com.example.store.dto;

import lombok.Data;

/** Request DTO for creating a new product. */
@Data
public class CreateProductRequest {
    private String description;
}
