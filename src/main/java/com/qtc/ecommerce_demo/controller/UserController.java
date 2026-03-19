package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.service.UserService;
import lombok.RequiredArgsConstructor;
/*
构造器：
注解                作用        生成内容

@NoArgsConstructor 生成无参构造器 public ClassName() {}

@AllArgsConstructor 生成全参构造器 public ClassName(所有字段) {}

@RequiredArgsConstructor​ 生成必需参构造器​ public ClassName(final字段+@NonNull字段) {}​

@Builder 生成建造者模式构造器 链式调用构造器
 */
/*
定义构造器（在类中编写）：

java
下载
复制
public class UserService {
    private UserRepository userRepository;

    // 定义构造器
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }
}

使用构造器（创建对象时调用）：

java
下载
复制
// 使用构造器创建对象
UserRepository repo = new UserRepository();
UserService service = new UserService(repo);  // 这里调用构造器
 */
import org.springframework.web.bind.annotation.*;
/*
星号（*）​ 表示通配符导入，意思是导入指定包下的所有公共类、接口、枚举和注解

Spring Web MVC 注解（org.springframework.web.bind.annotation.*）

定位：Web 控制器层，处理 HTTP 请求和响应

作用：定义 RESTful API、处理前端请求、路由分发
 */
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
/*
@RestController：

一个组合注解，包含：

@Controller：标记为 Spring MVC 控制器

Spring MVC 控制器起到了HTTP请求处理器和业务逻辑协调者的关键作用

@ResponseBody：自动将返回值转为 JSON

表示这个类处理 HTTP 请求

返回的数据会自动转为 JSON 格式

@RequestMapping("/api/users")：

映射 HTTP 请求路径

以 /api/users开头的请求都会路由到这个控制器

是类级别的路径前缀

@RequiredArgsConstructor：

Lombok 注解

自动生成构造器

为所有 final字段生成带参构造器

作用相当于：
public UserController(UserService userService) {
    this.userService = userService;
}
 */
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    /*
    接收 GET /api/users/123请求

从路径获取 ID 123

调用服务层查询用户

返回用户信息的 JSON
     */

    @GetMapping//处理 HTTP GET 请求，用于获取/查询资源
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping//处理 HTTP POST 请求，用于创建新资源
    public Long createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
/*
方式1：不同路径 + 相同 HTTP 方法 ✅
java
下载
复制
@GetMapping("/{id}")      // GET /api/users/123
@GetMapping("/search")     // GET /api/users/search?name=xxx
// 不同路径，相同方法
方式2：相同路径 + 不同 HTTP 方法 ✅
java
下载
复制
@GetMapping              // GET /api/users
@PostMapping             // POST /api/users
@PutMapping              // PUT /api/users
@DeleteMapping           // DELETE /api/users
// 相同路径，不同方法
方式3：相同路径 + 相同方法 + 不同参数 ✅
java
下载
复制
@GetMapping(params = "type=simple")    // GET /api/users?type=simple
@GetMapping(params = "type=detail")    // GET /api/users?type=detail
// 相同路径和方法，但请求参数不同
方式4：相同路径 + 相同方法 + 不同内容类型 ✅
java
下载
复制
@GetMapping(produces = "application/json")  // Accept: application/json
@GetMapping(produces = "application/xml")   // Accept: application/xml
// 相同路径和方法，但Accept头不同
 */
/*@RestController
@RequestMapping("/api/users")
public class UserController {

    // 简单视图 - 只返回基本信息
    @GetMapping(params = "type=simple")
    public List<UserSimpleDTO> getUsersSimple() {
        // 返回：只有 id, name, email
        return userService.getUsersSimple();
    }

    // 详细视图 - 返回所有信息
    @GetMapping(params = "type=detail")
    public List<UserDetailDTO> getUsersDetail() {
        // 返回：所有字段，包括敏感信息外的所有数据
        return userService.getUsersDetail();
    }

    // 完整视图 - 包含关联数据
    @GetMapping(params = "type=full")
    public List<UserFullDTO> getUsersFull() {
        // 返回：用户信息 + 订单 + 地址 + 等关联数据
        return userService.getUsersFull();
    }
}
*/



/*

 */



/*
2. 基于内容类型的映射 (produces)
基本概念

produces属性允许您根据客户端接受的内容类型（Accept 头）来返回不同格式的数据。

在您的代码中的示例
java
下载
复制
@RestController
@RequestMapping("/api/users")
public class UserController {

    // 返回 JSON 格式
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserJson(@PathVariable Long id) {
        // 处理 Accept: application/json
        return userService.getUserById(id);
    }

    // 返回 XML 格式
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public User getUserXml(@PathVariable Long id) {
        // 处理 Accept: application/xml
        return userService.getUserById(id);
    }

    // 返回纯文本格式
    @GetMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUserText(@PathVariable Long id) {
        // 处理 Accept: text/plain
        User user = userService.getUserById(id);
        return String.format("用户ID: %d, 用户名: %s", user.getId(), user.getUsername());
    }
}
HTTP 请求示例
http
复制
# 请求1：获取 JSON
GET /api/users/123
Accept: application/json
# 返回：{"id":123,"username":"张三","email":"zhangsan@example.com"}

# 请求2：获取 XML
GET /api/users/123
Accept: application/xml
# 返回：<user><id>123</id><username>张三</username><email>zhangsan@example.com</email></user>

# 请求3：获取纯文本
GET /api/users/123
Accept: text/plain
# 返回：用户ID: 123, 用户名: 张三
 */