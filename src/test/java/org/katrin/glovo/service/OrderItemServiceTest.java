package org.katrin.glovo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.repository.OrderItemRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void getById_ok() {
        int id = 1;
        OrderItemDto orderItemDto = OrderItemDto.builder().id(id).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        when(orderItemRepository.findById(1)).thenReturn(Optional.ofNullable(orderItemEntity));

        OrderItemDto actual = orderItemService.getById(id);

        assertEquals(actual, orderItemDto);
        assertEquals(actual.getId(), id);
        verify(orderItemRepository, times(1)).findById(id);
    }

    @Test
    public void getById_notFound() {
        int id = 1;

        when(orderItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderItemService.getById(id));
        verify(orderItemRepository, times(1)).findById(id);
    }

    @Test
    public void getAll_ok() {
        int orderId = 1;
        List<OrderItemDto> orderItemDtos = List.of(OrderItemDto.builder().orderId(orderId).build(), OrderItemDto.builder().orderId(orderId).build());
        List<OrderItemEntity> orderItemEntities = orderItemDtos.stream().map(OrderItemConverter::toEntity).toList();

        when(orderItemRepository.findAll()).thenReturn(orderItemEntities);

        List<OrderItemDto> actual = orderItemService.getAll();

        assertEquals(actual, orderItemDtos);
        assertEquals(actual.size(), orderItemEntities.size());
        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    public void findByOrderId_ok() {
        int orderId = 1;
        List<OrderItemDto> orderItemDtos = List.of(OrderItemDto.builder().orderId(orderId).build(), OrderItemDto.builder().orderId(orderId).build());
        List<OrderItemEntity> orderItemEntities = orderItemDtos.stream().map(OrderItemConverter::toEntity).toList();

        when(orderItemRepository.findByOrderId(orderId)).thenReturn(orderItemEntities);

        List<OrderItemDto> actual = orderItemService.findByOrderId(orderId);

        assertEquals(actual, orderItemDtos);
        assertEquals(actual.size(), orderItemEntities.size());
        verify(orderItemRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    public void update_ok() {
        int id = 1;
        OrderItemDto orderItemDto = OrderItemDto.builder().id(id).build();
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);

        when(orderItemRepository.save(any(OrderItemEntity.class))).thenReturn(orderItemEntity);

        OrderItemDto actual = orderItemService.update(orderItemDto);

        assertEquals(actual, orderItemDto);
        verify(orderItemRepository, times(1)).save(any(OrderItemEntity.class));
    }

    @Test
    public void deleteById_ok() {
        int id = 1;

        doNothing().when(orderItemRepository).deleteById(id);
        orderItemService.delete(id);

        verify(orderItemRepository, times(1)).deleteById(id);
    }
}
