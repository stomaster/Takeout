package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:63342", "http://localhost:8081"},
        allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    // ---------- 新增：登录接口 ----------
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> response = new HashMap<>();

        // 调用服务层验证登录
        User user = userService.login(username, password);

        if (user != null) {
            // 登录成功
            response.put("code", 200);
            response.put("message", "登录成功");
            response.put("data", user);
        } else {
            // 登录失败
            response.put("code", 400);
            response.put("message", "用户名或密码错误");
        }

        return response;
    }

    // ---------- 新增：注册接口 ----------
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 调用服务层创建用户
            Long userId = userService.createUser(user);

            response.put("code", 200);
            response.put("message", "注册成功");
            response.put("data", userId);
        } catch (Exception e) {
            // 注册失败
            response.put("code", 400);
            response.put("message", "注册失败: " + e.getMessage());
        }

        return response;
    }

    // 获取单个用户
    // 在 @GetMapping("/{id}") 之前添加这个
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}