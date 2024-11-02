package org.katrin.glovo.integration;

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
    private ProductDto prDto1 = ProductDto.builder()
            .name("Product 1")
            .country("Ukraine")
            .stockQuantity(12)
            .price(34.12)
            .build();
    private ProductDto prDto2 = ProductDto.builder()
            .name("Product 2")
            .price(67.89).build();


    @BeforeEach
    public void init() {
        productRepository.deleteAll();
    }

    @Test
    public void saveTest_uniqueName_ok() throws Exception {
        productService.save(prDto1);
        ProductDto prDto2 = ProductDto.builder().name("Product 2").country("Ukraine").stockQuantity(12).price(34.12).build();
        mockMvc.perform(post("/products")
                        .content(mapper.writeValueAsString(prDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(prDto2.getName()))
                .andExpect(jsonPath("$.price").value(prDto2.getPrice()))
                .andExpect(jsonPath("$.country").value("Ukraine"));
    }

    @Test
    public void saveTest_notUniqueNameButCountry_ok() throws Exception {
        productService.save(prDto1);
        ProductDto prDto2 = ProductDto.builder().name("Product 2").country("France").stockQuantity(12).price(34.12).build();
        mockMvc.perform(post("/products")
                        .content(mapper.writeValueAsString(prDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(prDto2.getName()))
                .andExpect(jsonPath("$.price").value(prDto2.getPrice()))
                .andExpect(jsonPath("$.country").value("France"));
    }

    @Test
    void saveTest_notUnique_shouldReturnBadRequest() throws Exception {
        productService.save(prDto1);
        String jsonProduct = "{ \"name\": \"Product 1\", \"stockQuantity\": -1, \"price\": 20.0, \"country\": \"Ukraine\" }";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTest_negativeStockQuantity_shouldReturnBadRequest() throws Exception {
        String jsonProduct = "{ \"name\": \"Test Product\", \"stockQuantity\": -1, \"price\": 20.0, \"country\": \"Ukraine\" }";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTest_negativePrice_shouldReturnBadRequest() throws Exception {
        String jsonProduct = "{ \"name\": \"Test Product\", \"stockQuantity\": 1, \"price\": -20.0, \"country\": \"Ukraine\" }";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTest_negativePriceAndNegativeStockQuantity_shouldReturnBadRequest() throws Exception {
        String jsonProduct = "{ \"name\": \"Test Product\", \"stockQuantity\": -1, \"price\": -20.0, \"country\": \"Ukraine\" }";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
    }





//    @Test
//    public void getAllTest() throws Exception {
//        prDto1 = productService.save(prDto1);
//        prDto2 = productService.save(prDto2);
//
//        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(prDto1, prDto2)), true));
//    }
//
//    @Test
//    public void getByIdTest() throws Exception {
//        prDto1 = productService.save(prDto1);
//        int id = prDto1.getId();
//
//        mockMvc.perform(get("/products/{id}", id).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(prDto1), true));
//    }

//    @Test
//    public void updateTest() throws Exception {
//        prDto1 = productService.save(prDto1);
//        prDto1.setName("Product 1 UPDATED");
//
//        mockMvc.perform(put("/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(prDto1)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(prDto1), true));
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        prDto1 = productService.save(prDto1);
//        int id = prDto1.getId();
//
//        mockMvc.perform(delete("/products/{id}", id).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/products/{id}", id).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
}
