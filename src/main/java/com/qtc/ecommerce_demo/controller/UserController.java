package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public Long createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}