package org.katrin.glovo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.repository.ProductRepository;
import org.katrin.glovo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        ProductDto prDto1 = ProductDto.builder().name("Product 1").price(34.12).build();
        ProductDto prDto2 = ProductDto.builder().name("Product 2").price(67.89).build();

        prDto1 = productService.save(prDto1);
        prDto2 = productService.save(prDto2);

        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(prDto1, prDto2)), true));
    }

    @Test
    public void getByIdTest() throws Exception {
        ProductDto prDto = ProductDto.builder().name("Product 1").price(34.12).build();

        prDto = productService.save(prDto);
        int id = prDto.getId();

        mockMvc.perform(get("/products/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(prDto), true));
    }

    @Test
    public void saveTest() throws Exception {
        ProductDto prDto = ProductDto.builder().name("Product 1").price(34.12).build();

        mockMvc.perform(post("/products")
                        .content(mapper.writeValueAsString(prDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(prDto.getName()))
                .andExpect(jsonPath("$.price").value(prDto.getPrice()))
                .andExpect(jsonPath("$.country").value("Ukraine"));
    }

    @Test
    public void updateTest() throws Exception {
        ProductDto prDto = ProductDto.builder().name("Product 1").price(34.12).build();

        prDto = productService.save(prDto);
        int id = prDto.getId();

        mockMvc.perform(post("/products")
                        .content(mapper.writeValueAsString(prDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(prDto.getName()))
                .andExpect(jsonPath("$.price").value(prDto.getPrice()))
                .andExpect(jsonPath("$.country").value("Ukraine"));
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable int id) {
//        productService.delete(id);
//    }
}
