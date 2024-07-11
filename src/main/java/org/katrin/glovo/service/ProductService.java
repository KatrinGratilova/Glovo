package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.ProductConverter;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.entity.ProductEntity;
import org.katrin.glovo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor

@Service
public class ProductService {
    private ProductRepository productRepository;

    public Collection<ProductDto> getAll() {
        return productRepository.findAll().stream().map(ProductConverter::toDto).toList();
    }

    public ProductDto getById(int id) {
        return productRepository.findById(id).map(ProductConverter::toDto).orElseThrow();
    }

    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity = productRepository.save(ProductConverter.toEntity(productDto));
        return ProductConverter.toDto(productEntity);
    }

    public ProductDto update(ProductDto productDto) {
        ProductEntity productEntity = productRepository.save(ProductConverter.toEntity(productDto));
        return ProductConverter.toDto(productEntity);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
