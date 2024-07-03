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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        orderService.deleteById(id);
    }

    @GetMapping("/{id}/items")
    public Collection<Integer> getItems(@PathVariable int id) {
        return orderService.getItems(id);
    }

    @PatchMapping("/{orderId}/items")
    public OrderDto addItem(@PathVariable int orderId, @RequestBody OrderItemDto orderItemDto) {
        return orderService.addItem(orderId, orderItemDto);
    }

    @PatchMapping("/{orderId}/items/{orderItemId}")
    public OrderDto addItem(@PathVariable int orderId, @PathVariable int orderItemId) {
        return orderService.deleteItem(orderId, orderItemId);
    }
}
