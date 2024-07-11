package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @GetMapping()
    public Collection<OrderDto> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable int id) {
        return orderService.getById(id);
    }

    @PostMapping
    public OrderDto save(@RequestBody OrderDto orderDto) {
        return orderService.save(orderDto);
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable int id, @RequestBody OrderDto orderDto) {
        orderDto.setId(id);
        return orderService.update(orderDto);
    }

    @PostMapping("/{orderId}/items")
    public OrderDto addItem(@PathVariable int orderId, @RequestBody OrderItemDto orderItemDto) {
        return orderService.addItem(orderId, orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        orderService.delete(id);
    }
}
