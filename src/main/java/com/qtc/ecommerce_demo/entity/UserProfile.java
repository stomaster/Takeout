package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
1. import java.util.ArrayList;

作用：引入 java.util包中的 ArrayList类。

效果：引入后，你就可以在自己的类中直接写 ArrayList<String> list = new ArrayList<>();来使用动态数组了。

不引入会怎样：你必须使用类的全限定名，即 java.util.ArrayList<String> list = new java.util.ArrayList<>();，代码会变得冗长。

2. import java.util.Arrays;

作用：引入 java.util包中的 Arrays工具类。

这个类提供了一系列静态方法，用于操作数组（如排序、搜索、比较、填充、转换为列表等），非常实用。

常用方法示例：

java
下载
复制
int[] numbers = {3, 1, 4, 1, 5};
Arrays.sort(numbers); // 排序数组 -> {1, 1, 3, 4, 5}
int index = Arrays.binarySearch(numbers, 4); // 二分查找
String str = Arrays.toString(numbers); // 转换为字符串 "[1, 1, 3, 4, 5]"
List<Integer> list = Arrays.asList(1, 2, 3); // 将一组值转换为固定大小的列表,变成{1,2,3}
3. import java.util.List;

作用：引入 java.util包中的 List接口。

正如我们之前讨论的，List是一个接口，它定义了列表的行为规范。引入它后，你可以用 List类型的引用来指向它的各种实现（如 ArrayList、LinkedList），这是面向接口编程的推荐做法。

关于 java.util包

这三个类都属于 java.util​ 包。

java.util​ 是 Java 核心类库中最重要的包之一，被称为“工具包”（Utility Package）。

它包含了大量常用的工具类、接口和数据结构，例如：

集合框架：List, Set, Map, ArrayList, HashMap等。

工具类：Arrays, Collections（集合工具类）。

日期时间（旧版）：Date, Calendar。

其他：Random, Scanner, Objects等。
 */

@Data
public class UserProfile {
    private Long id;
    private Long userId;
    private String avatarUrl;
    private String nickname;
    private String signature;
    private Integer viewCount = 0;
    private Integer collectCount = 0;
    private Integer purchaseCount = 0;
    private String grade;
    private String school;
    private String college;
    private String studentId;
    private String tags;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public List<String> getTagList() {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();//返回一个空列表，输出[]
        }
        return Arrays.asList(tags.split(","));
    }
/*
tagList是传入的新标签，格式是A，B,C
 */
    public void setTagList(List<String> tagList) {
        if (tagList == null || tagList.isEmpty()) {
            this.tags = null;
        } else {
            this.tags = String.join(",", tagList);
        }
    }
}
/*
用户在profile界面设置细节个人信息，保存后即可显示。
userid，nickname，signature，viewcount，一直到tags，都要显示
用户自定义标签的功能暂时先算了，，感觉这里用‘，’连接怪怪的
可以上传头像？？
 */