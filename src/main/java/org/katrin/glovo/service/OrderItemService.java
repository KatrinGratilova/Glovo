package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemDto getById(int id) {
        return orderItemRepository.findById(id).map(OrderItemConverter::toDto).orElseThrow();
    }

    public Collection<OrderItemDto> getAll() {
        return orderItemRepository.findAll().stream().map(OrderItemConverter::toDto).toList();
    }

    public Collection<OrderItemDto> getByOrderId(int orderId) {
        return orderItemRepository.findByOrderId(orderId).stream().map(OrderItemConverter::toDto).toList();
    }

    public OrderItemDto update(OrderItemDto orderItemDto) {
        OrderItemEntity orderItemEntity = orderItemRepository.save(OrderItemConverter.toEntity(orderItemDto));
        return OrderItemConverter.toDto(orderItemEntity);
    }

    public void delete(int id) {
        orderItemRepository.deleteById(id);
    }
}
