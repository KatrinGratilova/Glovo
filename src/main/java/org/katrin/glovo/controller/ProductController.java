package org.katrin.glovo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @GetMapping()
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable int id) {
        return productService.getById(id);
    }

//    @PostMapping
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ProductDto save(@RequestBody ProductDto productDto) {
//        return productService.save(productDto);
//    }

    @PutMapping
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable int id) {
        productService.delete(id);
    }
}
