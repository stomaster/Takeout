package com.qtc.ecommerce_demo.service.impl;

import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.mapper.UserMapper;
import com.qtc.ecommerce_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

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