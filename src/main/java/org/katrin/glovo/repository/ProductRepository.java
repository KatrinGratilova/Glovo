package org.katrin.glovo.repository;

import org.katrin.glovo.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private final Map<Integer, ProductDto> collector = new HashMap<>();

    public Collection<ProductDto> getAll() {
        return collector.values();
    }

    public ProductDto getById(int id) {
        return collector.get(id);
    }

    public ProductDto save(ProductDto productDto) {
        return collector.put(productDto.getId(), productDto);
    }

    public ProductDto update(ProductDto productDto) {
        return collector.put(productDto.getId(), productDto);
    }

    public void delete(int id) {
        collector.remove(id);
    }
}
