package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.OrderConverter;
import org.katrin.glovo.converter.OrderItemConverter;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.entity.OrderEntity;
import org.katrin.glovo.entity.OrderItemEntity;
import org.katrin.glovo.exception.InsufficientStockException;
import org.katrin.glovo.exception.OrderException;
import org.katrin.glovo.repository.Order.OrderRepository;
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
        return OrderConverter.toDto(orderEntity);
    }

    public OrderDto updateWithoutItems(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.updateWithoutItems(OrderConverter.toEntity(orderDto));
        return OrderConverter.toDto(orderEntity);
    }

    public OrderDto addItem(int orderId, OrderItemDto orderItemDto) throws OrderException {
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItemDto);
        OrderEntity updatedOrder;
        try {
            updatedOrder = orderRepository.addItem(orderId, orderItemEntity);
        } catch (InsufficientStockException e) {   // Ловимо виключення недостатньої кількості товару
            throw new OrderException(e.getMessage());  // Обгортаємо в OrderException для всіх виключень з замовленнями
        }
        return OrderConverter.toDto(updatedOrder);
    }

    public List<OrderDto> getByClientId(int clientId) {
        return orderRepository.findByClientId(clientId).stream().map(OrderConverter::toDto).toList();
    }

    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
