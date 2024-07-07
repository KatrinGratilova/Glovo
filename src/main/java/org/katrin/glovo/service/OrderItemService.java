package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemDto getById(int id) {
        return orderItemRepository.getById(id);
    }

    public Collection<OrderItemDto> getAll() {
        return orderItemRepository.getAll();
    }

    public OrderItemDto update(OrderItemDto orderItemDto) {
        return orderItemRepository.update(orderItemDto);
    }

    public void delete(int id) {
        orderItemRepository.delete(id);
    }
}
