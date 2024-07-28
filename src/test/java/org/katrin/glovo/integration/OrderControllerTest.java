package org.katrin.glovo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.dto.ProductDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.ProductEntity;
import org.katrin.glovo.repository.OrderRepository;
import org.katrin.glovo.repository.ProductRepository;
import org.katrin.glovo.service.OrderService;
import org.katrin.glovo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private OrderDto orderDto1;
    private OrderDto orderDto2;

    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    public void init() {
        orderRepository.deleteAll();
        orderDto1 = OrderDto.builder()
                .customerName("Customer 1")
                .checkoutDate(LocalDateTime.of(12, 12, 12, 12, 12))
                .build();
        orderDto2 = OrderDto.builder()
                .customerName("Customer 2")
                .build();
    }

    @Test
    public void getAllTest() throws Exception {
        orderDto2.setCheckoutDate(LocalDateTime.now());

        orderDto1 = orderService.save(orderDto1);
        orderDto2 = orderService.save(orderDto2);

        mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(orderDto1, orderDto2)), true));
    }

    @Test
    public void getByIdTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int id = orderDto1.getId();

        mockMvc.perform(get("/orders/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderDto1), true));
    }

    @Test
    public void saveTest() throws Exception {
        mockMvc.perform(post("/orders")
                        .content(mapper.writeValueAsString(orderDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.customerName").value(orderDto2.getCustomerName()))
                .andExpect(jsonPath("$.status").value("IN_PROCESSING"))
                .andExpect(jsonPath("$.checkoutDate").exists());
    }

    @Test
    public void updateWithoutItemsTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        orderDto1.setCustomerName("Customer 1 UPDATED");

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderDto1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderDto1), true));
    }

    @Test
    public void getItemsTest() throws Exception {
        OrderEntity orderEntity1 = OrderEntity.builder()
                .customerName("Customer 1")
                .checkoutDate(LocalDateTime.of(12, 12, 12, 12, 12))
                .items(new ArrayList<>())
                .build();
        orderEntity1 = orderRepository.save(orderEntity1);
        int orderId = orderEntity1.getId();

        ProductEntity productEntity = ProductEntity.builder().name("Product 1").stockQuantity(12).build();
        productEntity = productRepository.save(productEntity);

        OrderItemEntity orderItemEntity1 = OrderItemEntity.builder().quantity(456).price(34.12).product(productEntity).order(orderEntity1).build();

        orderEntity1.getItems().add(orderItemEntity1);
        orderRepository.save(orderEntity1);

        mockMvc.perform(get("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productId").value(productEntity.getId()));
    }

    @Test
    public void addItemTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int orderId = orderDto1.getId();

        ProductDto productDto = ProductDto.builder().name("Product 1").stockQuantity(12).build();
        productDto = productService.save(productDto);


        OrderItemDto orderItemDto = OrderItemDto.builder()
                .quantity(456)
                .price(34.12)
                .productId(productDto.getId())
                .build();

        mockMvc.perform(post("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.items").isNotEmpty());

        mockMvc.perform(get("/items/{id}", orderId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.price").value(orderItemDto.getPrice()))
                .andExpect(jsonPath("$.quantity").value(orderItemDto.getQuantity()))
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    public void deleteTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int id = orderDto1.getId();

        mockMvc.perform(delete("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
