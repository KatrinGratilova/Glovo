package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemDto> getAll() {
        return orderItemService.getAll();
    }

    @GetMapping("/{id}")
    public OrderItemDto getById(@PathVariable int id) {
        return orderItemService.getById(id);
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
