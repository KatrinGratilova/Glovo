package org.katrin.glovo.controller;

import lombok.RequiredArgsConstructor;
import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-panel")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @PutMapping("/users/{id}")
    public UserCreateDto assignAdminRole(@PathVariable int id) {
        return userService.assignAdminRole(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteById(id);
    }
}
