package com.qtc.ecommerce_demo.service;  // 这行IDEA会自动生成

import com.qtc.ecommerce_demo.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    Long createUser(User user);
}