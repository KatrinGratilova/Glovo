package org.katrin.glovo.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.converter.ProductConverter;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.ProductEntity;
import org.katrin.glovo.exception.InsufficientStockException;
import org.katrin.glovo.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void saveTest_uniqueName_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder()
                .id(id).name("Bread").stockQuantity(12).price(12.12).country("Ukraine").build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDto actual = productService.save(productDto);

        assertEquals(actual, productDto);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void saveTest_notUniqueNameButCountry_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder()
                .id(id).name("Bread").stockQuantity(12).price(12.12).country("Ukraine").build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDto actual = productService.save(productDto);

        assertEquals(actual, productDto);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void saveTest_notUnique() {
        int productId = 1;
        ProductDto productDto = ProductDto.builder()
                .id(productId).name("Bread").stockQuantity(-12).price(12.12).country("Ukraine").build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(productEntity))
                .thenThrow(new DataIntegrityViolationException("A product with this name and country already exists.", null));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> productService.save(productDto));

        assertEquals("A product with this name and country already exists.", exception.getMessage());
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    public void saveTest_negativeQuantity() {
        int productId = 1;
        ProductDto productDto = ProductDto.builder()
                .id(productId).name("Bread").stockQuantity(-12).price(12.12).country("Ukraine").build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(productEntity)).thenThrow(new ConstraintViolationException("Quantity cannot be negative.", null));

        Exception exception = assertThrows(ConstraintViolationException.class, () -> productService.save(productDto));

        assertEquals("Quantity cannot be negative.", exception.getMessage());
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    public void saveTest_negativePrice() {
        int productId = 1;
        ProductDto productDto = ProductDto.builder()
                .id(productId).name("Bread").stockQuantity(12).price(-12.12).country("Ukraine").build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(productEntity))
                .thenThrow(new ConstraintViolationException("Price cannot be negative", null));

        Exception exception = assertThrows(ConstraintViolationException.class, () -> productService.save(productDto));

        assertEquals("Price cannot be negative", exception.getMessage());
        verify(productRepository, times(1)).save(productEntity);
    }


//    @Test
//    public void getAllTest_ok() {
//        List<ProductDto> productDtos = List.of(new ProductDto(), new ProductDto());
//        List<ProductEntity> productEntities = productDtos.stream().map(ProductConverter::toEntity).toList();
//
//        when(productRepository.findAll()).thenReturn(productEntities);
//
//        List<ProductDto> actual = productService.getAll();
//
//        assertEquals(actual, productDtos);
//        assertEquals(actual.size(), productEntities.size());
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void getByIdTest_ok() {
//        int id = 1;
//        ProductDto productDto = ProductDto.builder().id(id).build();
//        ProductEntity productEntity = ProductConverter.toEntity(productDto);
//
//        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(productEntity));
//
//        ProductDto actual = productService.getById(id);
//
//        assertEquals(actual, productDto);
//        assertEquals(actual.getId(), id);
//        verify(productRepository, times(1)).findById(id);
//    }
//
//    @Test
//    public void getByIdTest_notFound() {
//        int id = 1;
//
//        when(productRepository.findById(1)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> productService.getById(id));
//        verify(productRepository, times(1)).findById(id);
//    }


//    @Test
//    public void updateTest_ok() {
//        int id = 1;
//        ProductDto productDto = ProductDto.builder().id(id).build();
//        ProductEntity productEntity = ProductConverter.toEntity(productDto);
//
//        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
//
//        ProductDto actual = productService.save(productDto);
//
//        assertEquals(actual, productDto);
//        verify(productRepository, times(1)).save(any(ProductEntity.class));
//    }
//
//    @Test
//    public void deleteByIdTest_ok() {
//        int id = 1;
//
//        doNothing().when(productRepository).deleteById(id);
//        productService.delete(id);
//
//        verify(productRepository, times(1)).deleteById(id);
//    }
}
