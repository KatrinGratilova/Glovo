package org.katrin.glovo.converter;

import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.entity.ProductEntity;

public class OrderItemConverter {
    public static OrderItemDto toDto(OrderItemEntity orderItemEntity) {
        return OrderItemDto.builder()
                .id(orderItemEntity.getId())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .orderId(orderItemEntity.getOrder().getId())
                .productId(orderItemEntity.getProduct().getId())
                .build();
    }

    public static OrderItemEntity toEntity(OrderItemDto orderItemDto) {
        return OrderItemEntity.builder()
                .id(orderItemDto.getId())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .product(ProductEntity.builder().id(orderItemDto.getProductId()).build())
                .order(OrderEntity.builder().id(orderItemDto.getOrderId()).build())
                .build();
    }
}
