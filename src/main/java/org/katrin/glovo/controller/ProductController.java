package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @GetMapping()
    public Collection<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable int id, @RequestBody ProductDto productDto) {
        productDto.setId(id);
        return productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        productService.deleteById(id);
    }
}
