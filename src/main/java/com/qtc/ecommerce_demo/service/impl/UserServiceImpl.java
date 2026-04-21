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
import org.springframework.util.DigestUtils;

/*
导入注解：导入 @Service注解的类定义

启用注解功能：让编译器知道 @Service注解的存在

Spring 容器管理：配合 Spring 的组件扫描机制
 */
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
/*
stereotype是 Spring 框架中的一个核心注解包，包含用于声明组件类型和角色的标识性注解。

包结构：
复制
org.springframework.stereotype
├── Component
├── Service
├── Repository
├── Controller
└── Indexed
 */
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
    /*
    构造器省略了：
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;  // ✅ 必须的初始化
    }

    在主函数里，
    public class Test {
    public static void main(String[] args) {
        // 这行代码发生了什么？
        UserServiceImpl service = new UserServiceImpl(userMapper);
        // 1. new：分配内存
        // 2. UserServiceImpl(userMapper)：调用构造器
        // 3. 执行构造器中的 this.userMapper = userMapper
        // 4. 将创建的对象赋值给 service
    }
    }

     */
    /*
    实现接口方法要override
    确保你确实是实现接口方法

防止方法名拼写错误

编译器会检查方法签名是否匹配
     */
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
        // 1. 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 检查邮箱是否已存在
        User existingEmail = userMapper.findByEmail(user.getEmail());
        if (existingEmail != null) {
            throw new RuntimeException("邮箱已被注册");
        }


        user.setPassword(user.getPassword());

        // 4. 插入用户
        int result = userMapper.insert(user);//userMapper生成的mybatis代理会有result返回值，比如插入的用户的id
        if (result <= 0) {
            throw new RuntimeException("用户注册失败");
        }

        // 5. 返回用户ID
        return user.getId();
    }
    @Override
    public User login(String username, String password) {
        // 首先尝试通过用户名查找用户
        User userByUsername = userMapper.findByUsername(username);
        if (userByUsername != null && userByUsername.getPassword().equals(password)) {
            return userByUsername;
        }

        // 如果通过用户名没找到，尝试通过邮箱查找
        User userByEmail = userMapper.findByEmail(username);
        if (userByEmail != null && userByEmail.getPassword().equals(password)) {
            return userByEmail;
        }

        return null; // 用户名/邮箱或密码错误
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