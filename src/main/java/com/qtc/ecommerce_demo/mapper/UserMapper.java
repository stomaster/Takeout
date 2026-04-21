package com.qtc.ecommerce_demo.mapper;
import com.qtc.ecommerce_demo.entity.User;
import org.apache.ibatis.annotations.Mapper; //生成实现类；把java换成sql；支持xml和这里混用
/*
MyBatis 注解（org.apache.ibatis.annotations.*）

定位：数据访问层，处理数据库操作

作用：定义 SQL 查询、映射数据库结果
 */
/*
org.apache.ibatis.annotations
├── org         (顶级域名 - 组织)
├── apache      (组织名 - Apache软件基金会)
├── ibatis      (项目名 - MyBatis 原名 iBATIS)
├── annotations (包名 - 注解相关类)
 */
/*
4. MyBatis 的其他常用注解
注解 作用 对应的 XML 标签
@Insert插入语句<insert>
@Update更新语句<update>
@Delete 删除语句<delete>
@Results 结果映射 <resultMap>
@Result 字段映射 <result>
@One 一对一关联 <association>
@Many 一对多关联 <collection>
 */
/*
导入 @Mapper注解

这个注解标记当前接口是一个 MyBatis 的 Mapper 接口

Spring Boot 会自动扫描带有 @Mapper注解的接口，并为其创建实现类
 */
import org.apache.ibatis.annotations.Param;
/*
导入 @Param注解

用于给 Mapper 方法参数命名，以便在 XML 或 SQL 注解中引用
 */
import org.apache.ibatis.annotations.Select;
/*
导入 @Select注解

用于直接在方法上编写 SQL 查询语句，避免编写 XML 文件
@Select("SELECT * FROM user WHERE id = #{id}")
User selectById(@Param("id") Long id);
 */
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")//注解方式，简单查询
    User selectById(@Param("id") Long id);//2.1 基本作用     将参数命名为"id"，SQL中可用#{id}引用

//    @Param注解用于给方法参数命名，使得在 XML 映射文件中可以使用指定的名称来引用参数。

    @Select("SELECT * FROM user")
    List<User> selectAll();

    // 注意：这里先用注解方式，XML方式下一步创建。@Select 导入后，UserMapper.xml 就没有该方法了吗？
    //
    //回答：不，两者可以共存，但有优先级规则。使用 @Select注解后，对应的 XML 配置不是必须的，但如果两者都存在，XML 会覆盖注解。
    int insert(User user);    //xml方式，复杂查询

    // 新增：通过用户名查询用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 新增：通过邮箱查询用户
    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username,
                                   @Param("password") String password);
}