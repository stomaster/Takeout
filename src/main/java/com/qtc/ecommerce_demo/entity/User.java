package com.qtc.ecommerce_demo.entity;

import lombok.Data; // Lombok，自动生成getter/setter等方法
import java.time.LocalDateTime;
import java.util.List;  // 用于定义列表

@Data
/*
@Data
作用：来自Lombok包的Data注解，编译时自动生成以下方法：

所有字段的getter方法

所有字段的setter方法

toString()方法

equals()和hashCode()方法

无参构造器

 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private UserProfile userProfile;              // 用户详细信息
    private List<UserCollection> collections;     // 用户收藏列表
}
/*
在注册页面获取username，password，email。id，createtime，updatetime系统自己填上去。
userprofile和collections可以用户在profile界面填写

登录时要名字密码对得上才能进去
 */