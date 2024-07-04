package org.katrin.glovo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.repository.ProductRepository;

import java.util.List;

public class ProductServiceTest {
    private ProductService productService;

    @Before
    public void setUp() {
        ProductRepository productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    public void getById_ok() {
        int id = 1;
        ProductDto expected = ProductDto.builder()
                .id(id)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();
        productService.save(expected);
        ProductDto actual = productService.getById(id);

        Assert.assertTrue(productService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_ok() {
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();
        productService.save(productDto);
        List<ProductDto> expected = List.of(productDto);
        List<ProductDto> actual = productService.getAll().stream().toList();

        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void save_ok() {
        int id = 1;
        ProductDto expected = ProductDto.builder()
                .id(id)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();
        productService.save(expected);
        ProductDto actual = productService.getById(id);

        Assert.assertTrue(productService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_ok() {
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();
        productService.save(productDto);

        ProductDto expected = ProductDto.builder()
                .id(1)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();

        ProductDto actual = productService.update(expected);

        Assert.assertTrue(productService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .name("bread")
                .country("Ukraine")
                .price(56.7)
                .stockQuantity(23)
                .build();
        productService.save(productDto);
        productService.delete(id);

        Assert.assertFalse(productService.getAll().contains(productDto));
    }
}
