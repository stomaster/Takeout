package com.qtc.ecommerce_demo.service.impl;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.mapper.UserMapper;
import com.qtc.ecommerce_demo.service.UserService;
/*
Controller → UserService（接口） → UserServiceImpl（实现）
    ↑                ↑
    └──注入接口───────┘
为什么这样设计？
文档中提到这种设计的好处：

解耦：Controller 只依赖接口，不依赖具体实现

灵活：可以随时更换实现类

易于测试：可以创建 Mock 实现进行单元测试

多态支持：可以有多个实现类
 */
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/*
导入注解：导入 @Service注解的类定义

启用注解功能：让编译器知道 @Service注解的存在

Spring 容器管理：配合 Spring 的组件扫描机制
 */
import java.util.List;

@Service
/*
//
1.有 @Service
@Service
public class UserServiceImpl {  // Spring 会创建并管理这个类的实例
    // ...
}
2.可以自动注入 @Service 标记的类 如autowired
 */
@RequiredArgsConstructor
/*
在构造器注入中的使用：
java
下载
复制
@Service
@RequiredArgsConstructor
public class UserService {
    // final 字段：必须通过构造器初始化
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
}
final 的具体作用：
1. 修饰变量：值不能改变
java
下载
复制
public class Example {
    private final int MAX_RETRY = 3;  // 常量，不能修改

    public void test() {
        // MAX_RETRY = 5;  // 错误！不能修改 final 变量
    }
}
2. 修饰字段：必须在构造器中初始化
java
下载
复制
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;  // final 字段

    // 构造器注入，初始化 final 字段
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;  // 必须在这里初始化
    }
}
3. 修饰方法：不能被子类重写
java
下载
复制
public class BaseService {
    public final void init() {  // final 方法
        // 初始化逻辑
    }
}

public class UserService extends BaseService {
    // @Override
    // public void init() {  // 错误！不能重写 final 方法
    // }
}
4. 修饰类：不能被继承
java
下载
复制
public final class StringUtils {  // final 类
    // 工具类通常设计为 final
}

// class MyUtils extends StringUtils {  // 错误！不能继承 final 类
// }
 */
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;  //构造器注入

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public Long createUser(User user) {
        userMapper.insert(user);
        return user.getId();
    }
}
/*
3.1 没有 @Autowired 的代码
java
下载
复制
public class UserController {
    private UserService userService;

    public UserController() {
        // 需要手动创建依赖
        this.userService = new UserServiceImpl();
    }
}
3.2 使用 @Autowired 的代码
java
下载
复制
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    // Spring 自动完成：this.userService = spring容器中的UserService实例
}
 */
/*
您的文档中展示了不同的注入方式：

4.1 字段注入（最常用）
java
下载
复制
@Autowired
private UserService userService;  // 直接注入到字段
4.2 构造器注入（推荐方式）

文档中也提到了这种方式：

java
下载
复制
@Service
@RequiredArgsConstructor
public class UserService {
    // 通过构造器自动注入
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Spring 会自动通过构造器注入
}
4.3 Setter 方法注入
java
下载
复制
private UserService userService;

@Autowired
public void setUserService(UserService userService) {
    this.userService = userService;
}
 */