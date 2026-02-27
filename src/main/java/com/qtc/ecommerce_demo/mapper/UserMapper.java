package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(@Param("id") Long id);

    @Select("SELECT * FROM user")
    List<User> selectAll();

    // 注意：这里先用注解方式，XML方式下一步创建
    int insert(User user);
}