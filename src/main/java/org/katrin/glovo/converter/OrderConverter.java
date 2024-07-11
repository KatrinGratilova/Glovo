package org.katrin.glovo.converter;

import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;

public class OrderConverter {
    public static OrderDto toDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .customerName(orderEntity.getCustomerName())
                .status(orderEntity.getStatus())
                .checkoutDate(orderEntity.getCheckoutDate())
                .items(orderEntity.getItems().stream().map(OrderItemEntity::getId).toList())
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto) {
        return OrderEntity.builder()
                .id(orderDto.getId())
                .customerName(orderDto.getCustomerName())
                .status(orderDto.getStatus())
                .checkoutDate(orderDto.getCheckoutDate())
                .build();
    }
}
