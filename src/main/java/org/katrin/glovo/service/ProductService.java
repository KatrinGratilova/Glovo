package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor

@Service
public class ProductService {
    private ProductRepository productRepository;

    public Collection<ProductDto> getAll() {
        return productRepository.getAll();
    }

    public ProductDto getById(int id) {
        return productRepository.getById(id);
    }

    public ProductDto save(ProductDto productDto) {
        return productRepository.save(productDto);
    }

    public ProductDto update(ProductDto productDto) {
        return productRepository.update(productDto);
    }

    public void delete(int id) {
        productRepository.delete(id);
    }
}
