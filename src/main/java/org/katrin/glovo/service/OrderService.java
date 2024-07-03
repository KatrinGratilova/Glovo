package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public Collection<OrderDto> getAll() {
        return orderRepository.getAll();
    }

    public OrderDto getById(int id) {
        return orderRepository.getById(id);
    }

    public OrderDto save(OrderDto order) {
        return orderRepository.save(order);
    }

    public OrderDto update(OrderDto order) {
        return orderRepository.update(order);
    }

    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    public List<Integer> getItems(int id) {
        return orderRepository.getItems(id);
    }

    public OrderDto addItem(int orderId, OrderItemDto orderItemDto) {
        return orderRepository.addItem(orderId, orderItemDto);
    }

    public OrderDto deleteItem(int orderId, int orderItemId) {
        return orderRepository.removeItem(orderId, orderItemId);
    }
}
