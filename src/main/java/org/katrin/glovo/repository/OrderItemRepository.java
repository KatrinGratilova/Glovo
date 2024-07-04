package org.katrin.glovo.repository;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderItemDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor

@Repository
public class OrderItemRepository {
    private final Map<Integer, OrderItemDto> collector = new HashMap<>();

    public OrderItemDto getById(int id) {
        return collector.get(id);
    }

    public Collection<OrderItemDto> getAll() {
        return collector.values();
    }

    public void save(OrderItemDto orderItemDto) {
        collector.put(orderItemDto.getId(), orderItemDto);
    }

    public OrderItemDto update(OrderItemDto orderItemDto) {
        collector.put(orderItemDto.getId(), orderItemDto);
        return collector.get(orderItemDto.getId());
    }

    public void delete(int orderItemId) {
        collector.remove(orderItemId);
    }
}
