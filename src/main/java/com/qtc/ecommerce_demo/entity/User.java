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

@Data自动生成哪个：是的，@Data注解的效果等同于同时使用了 @RequiredArgsConstructor注解（以及其他注解，如@Getter, @Setter
等）。它不会自动生成 @NoArgsConstructor。
在您的 User类中，所有字段都不是 final也没有 @NonNull注解，因此 @Data生成的 @RequiredArgsConstructor实际上就是
一个无参构造器。但这是一个危险的巧合。
无参构造器:用来User user=new User();

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