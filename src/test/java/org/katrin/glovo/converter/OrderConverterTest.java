package org.katrin.glovo.converter;

import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderStatus;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderConverterTest {
    @Test
    public void toDto() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1)
                .customerName("Katrin")
                .status(OrderStatus.IN_PROCESSING)
                .checkoutDate(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
                .items(List.of(OrderItemEntity.builder().id(1).build()))
                .build();

        OrderDto orderDto = OrderConverter.toDto(orderEntity);

        assertEquals(orderEntity.getId(), orderDto.getId());
        assertEquals(orderEntity.getCustomerName(), orderDto.getCustomerName());
        assertEquals(orderEntity.getStatus(), orderDto.getStatus());
        assertEquals(orderEntity.getCheckoutDate(), orderDto.getCheckoutDate());
        assertEquals(orderEntity.getItems().stream().map(OrderItemEntity::getId).toList(), orderDto.getItems());
    }

    @Test
    public void toEntity() {
        OrderDto orderDto = OrderDto.builder()
                .id(1)
                .customerName("Katrin")
                .status(OrderStatus.IN_PROCESSING)
                .checkoutDate(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
                .items(List.of(1))
                .build();

        OrderEntity orderEntity = OrderConverter.toEntity(orderDto);

        assertEquals(orderDto.getId(), orderEntity.getId());
        assertEquals(orderDto.getCustomerName(), orderEntity.getCustomerName());
        assertEquals(orderDto.getStatus(), orderEntity.getStatus());
        assertEquals(orderDto.getCheckoutDate(), orderEntity.getCheckoutDate());
        assertEquals(orderDto.getItems(), orderEntity.getItems().stream().map(OrderItemEntity::getId).toList());
    }
}
