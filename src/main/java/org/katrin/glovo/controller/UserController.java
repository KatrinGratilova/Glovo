package org.katrin.glovo.controller;

import lombok.AllArgsConstructor;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.UserDto;
import org.katrin.glovo.service.OrderService;
import org.katrin.glovo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getOrders(@PathVariable int id) {
        return orderService.getByClientId(id);
    }

    @PostMapping("/{id}/orders")
    public UserDto addOrder(@PathVariable int id, @RequestBody OrderDto orderDto){
        return userService.addOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}
