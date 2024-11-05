package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.dto.UserDto;
import org.katrin.glovo.service.OrderService;
import org.katrin.glovo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserCreateDto save(@RequestBody UserCreateDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserCreateDto update(@RequestBody UserCreateDto userDto) {
        return userService.updateWithoutOrders(userDto);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<OrderDto> getOrders(@PathVariable int id) {
        return orderService.getByClientId(id);
    }

    @PostMapping("/{id}/orders")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto addOrder(@PathVariable int id, @RequestBody OrderDto orderDto) {
        return userService.addOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable int id) {
        userService.deleteById(id);
    }
}
