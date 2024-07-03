package org.katrin.glovo.repository;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@AllArgsConstructor

@Repository
public class OrderRepository {
    private final Map<Integer, OrderDto> collector = new HashMap<>();
    private final OrderItemRepository orderItemRepository;

    public Collection<OrderDto> getAll() {
        return collector.values();
    }

    public OrderDto getById(int id) {
        return collector.get(id);
    }

    public OrderDto save(OrderDto order) {
        order.setItems(new ArrayList<>());
        return collector.put(order.getId(), order);
    }

    public OrderDto update(OrderDto order) {
        return collector.put(order.getId(), order);
    }

    public List<Integer> getItems(int id) {
        return collector.get(id).getItems();
    }

    public OrderDto addItem(int orderId, OrderItemDto orderItemDto) {
        orderItemRepository.save(orderItemDto);
        collector.get(orderId).getItems().add(orderItemDto.getId());
        return collector.get(orderId);
    }

    public OrderDto removeItem(int orderId, int orderItemId) {
        orderItemRepository.delete(orderItemId);
        collector.get(orderId).getItems().remove(orderItemId);
        return collector.get(orderId);
    }

    public void deleteById(int id) {
        collector.remove(id);
    }
}
