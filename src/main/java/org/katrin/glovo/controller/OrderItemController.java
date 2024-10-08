package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.service.OrderItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderItemDto> getAll() {
        return orderItemService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public OrderItemDto getById(@PathVariable int id) {
        return orderItemService.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public OrderItemDto update(@RequestBody OrderItemDto orderItemDto) {
        return orderItemService.update(orderItemDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteById(@PathVariable int id) {
        orderItemService.delete(id);
    }
}
