package org.katrin.glovo.converter;

import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderStatus;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderConverterTest {
    @Test
    public void toDtoTest() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1)
                .client(UserEntity.builder().id(1).build())
                .status(OrderStatus.IN_PROCESSING)
                .createdAt(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
                .items(List.of(OrderItemEntity.builder().id(1).build()))
                .build();

        OrderDto orderDto = OrderConverter.toDto(orderEntity);

        assertEquals(orderEntity.getId(), orderDto.getId());
        assertEquals(orderEntity.getClient().getId(), orderDto.getClientId());
        assertEquals(orderEntity.getStatus(), orderDto.getStatus());
        assertEquals(orderEntity.getCreatedAt(), orderDto.getCreatedAt());
        assertEquals(orderEntity.getItems().stream().map(OrderItemEntity::getId).toList(), orderDto.getItems());
    }

    @Test
    public void toEntityTest() {
        OrderDto orderDto = OrderDto.builder()
                .id(1)
                .clientId(1)
                .status(OrderStatus.IN_PROCESSING)
                .createdAt(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
                .items(List.of(1))
                .build();

        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        assertEquals(orderDto.getId(), orderEntity.getId());
        assertEquals(orderDto.getClientId(), orderEntity.getClient().getId());
        assertEquals(orderDto.getStatus(), orderEntity.getStatus());
        assertEquals(orderDto.getCreatedAt(), orderEntity.getCreatedAt());
        assertEquals(orderDto.getItems(), orderEntity.getItems().stream().map(OrderItemEntity::getId).toList());
    }
}
