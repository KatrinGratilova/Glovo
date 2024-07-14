package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.service.OrderItemService;
import org.katrin.glovo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private OrderItemService orderItemService;

    @GetMapping()
    public List<OrderDto> getAll() {
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

    @GetMapping("/{id}/items")
    public List<OrderItemDto> getItems(@PathVariable int id) {
        return orderItemService.findByOrderId(id);
    }

    @PostMapping("/{id}/items")
    public OrderDto addItem(@PathVariable int id, @RequestBody OrderItemDto orderItemDto) {
        return orderService.addItem(id, orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        orderService.delete(id);
    }
}
