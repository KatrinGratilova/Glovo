package org.katrin.glovo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.glovo.converter.OrderConverter;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.exception.InsufficientStockException;
import org.katrin.glovo.exception.OrderException;
import org.katrin.glovo.repository.Order.OrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void addItemTest_ok() throws InsufficientStockException, OrderException {
        int orderId = 1;
        int orderItemId = 12;

        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        OrderDto orderDto = OrderDto.builder().id(orderId).clientId(1).items(List.of(orderItemId)).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.addItem(orderId, orderItemEntity)).thenReturn(orderEntity);

        OrderDto actual = orderService.addItem(orderId, orderItemDto);

        assertEquals(actual, orderDto);
        assertTrue(actual.getItems().contains(orderItemId));
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void addItemTest_orderDoesNotExist() throws InsufficientStockException {
        int orderId = 1;
        int orderItemId = 12;
        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).orderId(orderId).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        // Настроюємо mock, щоб кинути виключення, якщо замовлення не знайдено
        when(orderRepository.addItem(orderId, orderItemEntity)).thenThrow(new EntityNotFoundException("Order not found."));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> orderService.addItem(orderId, orderItemDto));

        assertEquals("Order not found.", exception.getMessage());
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void addItemTest_productDoesNotExist() throws InsufficientStockException {
        int orderId = 1;
        int orderItemId = 12;
        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).orderId(orderId).productId(1).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        // Настроюємо mock, щоб кинути виключення, якщо продукт не знайдено
        when(orderRepository.addItem(orderId, orderItemEntity)).thenThrow(new EntityNotFoundException("Product not found."));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> orderService.addItem(orderId, orderItemDto));

        assertEquals("Product not found.", exception.getMessage());
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void addItemTest_insufficientStock() throws InsufficientStockException {
        int orderId = 1;
        int orderItemId = 12;
        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).orderId(orderId).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        // Налаштовуємо mock, щоб кинути виключення, якщо недостатньо товару на складі
        when(orderRepository.addItem(orderId, orderItemEntity)).thenThrow(new InsufficientStockException("Not enough stock available"));

        Exception exception = assertThrows(OrderException.class, () -> orderService.addItem(orderId, orderItemDto));

        assertEquals("Not enough stock available", exception.getMessage());
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void addItemTest_negativeQuantity() throws InsufficientStockException {
        int orderId = 1;
        int orderItemId = 12;

        // Створюємо DTO з негативною кількістю
        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).orderId(orderId).quantity(-5).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        // Налаштовуємо мок для викидання ConstraintViolationException з повідомленням про помилку
        when(orderRepository.addItem(orderId, orderItemEntity))
                .thenThrow(new ConstraintViolationException("Quantity cannot be negative.", null));

        Exception exception = assertThrows(ConstraintViolationException.class, () -> orderService.addItem(orderId, orderItemDto));

        assertEquals("Quantity cannot be negative.", exception.getMessage());
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

    @Test
    public void addItemTest_negativePrice() throws InsufficientStockException {
        int orderId = 1;
        int orderItemId = 12;

        // Створюємо DTO з негативною ціною
        OrderItemDto orderItemDto = OrderItemDto.builder().id(orderItemId).orderId(orderId).price(-10.00).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        // Налаштовуємо мок для викидання ConstraintViolationException з повідомленням про помилку
        when(orderRepository.addItem(orderId, orderItemEntity))
                .thenThrow(new ConstraintViolationException("Price cannot be negative", null));

        Exception exception = assertThrows(ConstraintViolationException.class, () -> orderService.addItem(orderId, orderItemDto));

        assertEquals("Price cannot be negative", exception.getMessage());
        verify(orderRepository, times(1)).addItem(orderId, orderItemEntity);
    }

/*

    @Test
    public void getAllTest_ok() {
        List<OrderDto> orderDtos = List.of(OrderDto.builder().clientId(1).items(new ArrayList<>()).build(),
                OrderDto.builder().id(1).items(new ArrayList<>()).build());
        List<OrderEntity> orderEntities = orderDtos.stream().map(OrderConverter::toEntity).toList();

        when(orderRepository.findAll()).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.getAll();

        assertEquals(actual, orderDtos);
        assertEquals(actual.size(), orderEntities.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getByIdTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(orderEntity));

        OrderDto actual = orderService.getById(id);

        assertEquals(actual, orderDto);
        assertEquals(actual.getId(), id);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    public void getByIdTest_notFound() {
        int id = 1;

        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void saveTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDto actual = orderService.save(orderDto);

        assertEquals(actual, orderDto);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void updateWithoutItemsTest_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder().id(id).items(new ArrayList<>()).build();
        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        when(orderRepository.updateWithoutItems(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDto actual = orderService.updateWithoutItems(orderDto);

        assertEquals(actual, orderDto);
        verify(orderRepository, times(1)).updateWithoutItems(any(OrderEntity.class));
    }

    @Test
    public void deleteByIdTest_ok() {
        int id = 1;

        doNothing().when(orderRepository).deleteById(id);
        orderService.delete(id);

        verify(orderRepository, times(1)).deleteById(id);
    }*/
}
