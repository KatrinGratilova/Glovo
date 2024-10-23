package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.exception.OrderException;
import org.katrin.glovo.service.OrderItemService;
import org.katrin.glovo.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private OrderItemService orderItemService;

    @GetMapping()
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDto getById(@PathVariable int id) {
        return orderService.getById(id);
    }

    @PutMapping
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDto updateWithoutItems(@RequestBody OrderDto orderDto) {
        return orderService.updateWithoutItems(orderDto);
    }

    @GetMapping("/{id}/items")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<OrderItemDto> getItems(@PathVariable int id) {
        return orderItemService.findByOrderId(id);
    }

    @PostMapping("/{id}/items")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDto addItem(@PathVariable int id, @RequestBody OrderItemDto orderItemDto) throws OrderException {
        return orderService.addItem(id, orderItemDto);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteById(@PathVariable int id) {
        orderService.delete(id);
    }
}
