package com.example.store.mapper;

import com.example.store.dto.CreateProductRequest;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.OrderItem;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/** MapStruct mapper for Product entity and DTOs. */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /** Convert Product entity to ProductDTO. Maps orderItems to list of order IDs. */
    @Mapping(source = "orderItems", target = "orderIds", qualifiedByName = "orderItemsToOrderIds")
    ProductDTO productToProductDTO(Product product);

    /** Convert list of Product entities to list of ProductDTOs. */
    List<ProductDTO> productsToProductDTOs(List<Product> products);

    /** Convert CreateProductRequest to Product entity. */
    Product createProductRequestToProduct(CreateProductRequest request);

    /** Helper method to extract order IDs from orderItems. */
    @Named("orderItemsToOrderIds")
    default List<Long> orderItemsToOrderIds(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return List.of();
        }
        return orderItems.stream().map(item -> item.getOrder().getId()).collect(Collectors.toList());
    }
}
