package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor

@RestController
@RequestMapping("/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public OrderItemDto getById(@PathVariable int id) {
        return orderItemService.getById(id);
    }

    @GetMapping
    public Collection<OrderItemDto> getAll() {
        return orderItemService.getAll();
    }

    @PutMapping("/{id}")
    public OrderItemDto update(@PathVariable int id, @RequestBody OrderItemDto orderItemDto) {
        orderItemDto.setId(id);
        return orderItemService.update(orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        orderItemService.delete(id);
    }
}
