package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
//什么是 "bind"（绑定）？
//
//"bind" 是"绑定"的意思，在 Spring MVC 中指将 HTTP 请求的数据绑定到 Java 方法的参数上。
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
/*
"@Controller是Spring MVC的核心注解，主要用于服务端渲染，返回视图名称。如果需要返回JSON，需要在方法上额外添加@ResponseBody注解。而
@RestController是@Controller和@ResponseBody的组合注解，专门为RESTful API设计，它的所有方法都会自动将返回值序列化为JSON。在现代前后端分离架构中，
我们通常使用@RestController。"

 */
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:63342", "http://localhost:8081"},
        allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserController {

    private final UserService userService;//Spring通过这个构造方法注入实例,自动创建UserController方法并注入userservice实例
    // ---------- 新增：登录接口 ----------
    /*
    // ❌ GET登录（危险！）
// 密码会暴露在URL、浏览器历史、服务器日志
GET /api/users/login?username=admin&password=123456

// ✅ POST登录（相对安全）
// 密码在请求体中，相对隐蔽
POST /api/users/login
Body: {"username": "admin", "password": "123456"}

"登录用POST主要基于三点考虑：第一是安全性，GET请求的参数会暴露在URL中，可能被浏览器历史、服务器日志记录，而POST请求的参数在请求体中，相对更安全；第
二是语义性，登录本质是创建一个会话或token，这符合POST'创建资源'的语义；第三是扩展性，POST没有URL长度限制，未来可以方便地添加验证码、设备信息等额外参数。"
     */
    @PostMapping("/login")//用户向服务器提交数据
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
    public Map<String, Object> register(@RequestBody User user) {//@RequestBody表示"从 HTTP 请求体中获取数据"，将请求体中的JSON/XML 自动转换为 Java 对象。
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
    @GetMapping("/{id}")//用户从服务器获得数据
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
