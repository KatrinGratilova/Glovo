package org.katrin.glovo.converter;

import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.entity.ProductEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductConverterTest {

    @Test
    public void testToDto() {
        ProductEntity productEntity = ProductEntity.builder()
                .id(1)
                .name("Test Product")
                .stockQuantity(100)
                .country("Ukraine")
                .price(99.99)
                .build();

        ProductDto productDto = ProductConverter.toDto(productEntity);

        assertEquals(productEntity.getId(), productDto.getId());
        assertEquals(productEntity.getName(), productDto.getName());
        assertEquals(productEntity.getStockQuantity(), productDto.getStockQuantity());
        assertEquals(productEntity.getCountry(), productDto.getCountry());
        assertEquals(productEntity.getPrice(), productDto.getPrice());
    }

    @Test
    public void testToEntity() {
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .name("Test Product")
                .stockQuantity(100)
                .country("Ukraine")
                .price(99.99)
                .build();

        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        assertEquals(productDto.getId(), productEntity.getId());
        assertEquals(productDto.getName(), productEntity.getName());
        assertEquals(productDto.getStockQuantity(), productEntity.getStockQuantity());
        assertEquals(productDto.getCountry(), productEntity.getCountry());
        assertEquals(productDto.getPrice(), productEntity.getPrice());
    }
}
