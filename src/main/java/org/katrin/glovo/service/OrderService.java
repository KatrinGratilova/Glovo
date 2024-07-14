package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.OrderConverter;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(OrderConverter::toDto).toList();
    }

    public OrderDto getById(int id) {
        return orderRepository.findById(id).map(OrderConverter::toDto).orElseThrow();
    }

    public OrderDto save(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.save(OrderConverter.toEntity(orderDto));
        return OrderConverter.toDto(orderEntity);  //TODO: default date, empty list
    }

    public OrderDto update(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.save(OrderConverter.toEntity(orderDto));
        return OrderConverter.toDto(orderEntity);  //TODO:
    }

    public OrderDto addItem(int orderId, OrderItemDto orderItemDto) {
        OrderEntity orderEntity = orderRepository.addItem(orderId, OrderItemConverter.toEntity(orderItemDto));
        return OrderConverter.toDto(orderEntity);
    }

    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
