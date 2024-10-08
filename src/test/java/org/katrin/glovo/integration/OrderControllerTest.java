package org.katrin.glovo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.*;
import org.katrin.glovo.repository.Order.OrderRepository;
import org.katrin.glovo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

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
    @Autowired
    private OrderItemService orderItemService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private OrderDto orderDto1;
    private OrderDto orderDto2;

    @BeforeEach
    public void init() {
        orderRepository.deleteAll();
        orderDto1 = OrderDto.builder()
                .clientId(1)
                .createdAt(LocalDateTime.of(12, 12, 12, 12, 12))
                .build();
        orderDto2 = OrderDto.builder()
                .clientId(2)
                .build();
    }

    @Test
    public void accessDeniedForUnauthenticatedUser() throws Exception {
        int randomOrderId = 123;

        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(12) // Або інше значення
                .orderId(randomOrderId)
                .build();

        mockMvc.perform(post("/orders/{id}/items", randomOrderId)
                        .contentType(MediaType.APPLICATION_JSON) // Встановлюємо тип контенту
                        .content(new ObjectMapper().writeValueAsString(orderItemDto))) // Додаємо тіло запиту
                .andExpect(status().isUnauthorized()); // Перевіряємо, що неавторизованому користувачу повернено 401
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

        orderDto1 = orderService.getById(orderId);
        int orderItemId = orderDto1.getItems().getFirst();

        mockMvc.perform(get("/items/{id}", orderItemId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.price").value(orderItemDto.getPrice()))
                .andExpect(jsonPath("$.quantity").value(orderItemDto.getQuantity()))
                .andExpect(jsonPath("$.orderId").value(orderId));
    }


    /*@Test
    public void getAllTest() throws Exception {
        orderDto2.setCreatedAt(LocalDateTime.now());

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
                .andExpect(jsonPath("$.customerName").value(orderDto2.getClientId()))
                .andExpect(jsonPath("$.status").value("IN_PROCESSING"))
                .andExpect(jsonPath("$.checkoutDate").exists());
    }

    @Test
    public void updateWithoutItemsTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        orderDto1.setClientId(1);

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderDto1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderDto1), true));
    }

    @Test
    public void getItemsTest() throws Exception {
        orderDto2 = orderService.save(orderDto2);
        int orderId = orderDto2.getId();

        ProductDto productDto = ProductDto.builder().name("Product 2").stockQuantity(12).build();
        productDto = productService.save(productDto);

        OrderItemDto orderItemDto = OrderItemDto.builder().quantity(456).price(34.12).productId(productDto.getId()).build();
        orderDto2 = orderService.addItem(orderDto2.getId(), orderItemDto);

        int orderItemId = orderDto2.getItems().getFirst();
        orderItemDto = orderItemService.getById(orderItemId);

        mockMvc.perform(get("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(orderItemDto))));
    }



    @Test
    public void deleteTest() throws Exception {
        orderDto1 = orderService.save(orderDto1);
        int id = orderDto1.getId();

        mockMvc.perform(delete("/orders/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/
}
