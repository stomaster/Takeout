📦 奶茶外卖网站 - 后端系统

🎯 项目简介

基于Spring Boot + MyBatis构建的奶茶外卖网站后端系统，为校园用户提供商品浏览、购物车、订单管理等一站式服务。

✨ 核心功能

• 用户管理：注册、登录、个人资料、成就标签

• 商品管理：商品展示、分类浏览、搜索筛选

• 购物车：商品添加、数量修改、批量操作

• 订单系统：下单、支付、订单状态跟踪

• 优惠系统：优惠券领取与使用

• 收藏功能：商品收藏与关注

🛠️ 技术栈

技术 版本 说明

Spring Boot 3.5.11 后端框架

Java 17+ 开发语言

MyBatis 3.0.5 ORM框架

MySQL 8.0+ 数据库

Maven 3.8+ 项目管理

Lombok 1.18+ 代码简化

Spring Security 6.3+ 安全认证

📁 项目结构


src/main/java/com/ecommerce/
├── config/          # 配置类
├── controller/      # 控制器层
├── service/         # 服务层
├── mapper/          # 数据访问层
├── model/          # 实体类
├── dto/            # 数据传输对象
├── exception/      # 异常处理
└── EcommerceDemoApplication.java


🚀 快速开始

环境要求

• JDK 17+

• MySQL 8.0+

• Maven 3.8+

• IDE推荐：IntelliJ IDEA 2023+

1. 克隆项目

git clone 
cd ecommerce-platform-backend


2. 数据库配置

# 创建数据库
mysql -u root -p
CREATE DATABASE ecommerce_demo;
exit

# 导入数据
mysql -u root -p ecommerce_demo < database/ecommerce_demo_backup.sql


3. 配置文件修改

修改 src/main/resources/application.yml：
spring:
datasource:
url: jdbc:mysql://localhost:3306/ecommerce_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
username: root        
password: 1234    
server:
port: 8081


4. 运行项目

# 方法一：通过IDE运行
# 直接运行 EcommerceDemoApplication.java

# 方法二：Maven命令
mvn spring-boot:run


5. 访问接口

• 项目启动后访问：http://localhost:8081

• Swagger API文档：http://localhost:8081/swagger-ui/index.html

• 健康检查：http://localhost:8081/actuator/health

📊 数据库设计

表名 说明 记录数

user 用户表 220

user_profile 用户资料 256

product 商品表 5

cart 购物车 7

order 订单表 0

coupon 优惠券 2

user_coupon 用户优惠券 1

achievement_tag 成就标签 5

📡 API接口文档

用户模块

方法 路径 说明

GET /api/users 获取用户列表

GET /api/users/{id} 获取用户详情

POST /api/users/register 用户注册

POST /api/users/login 用户登录

PUT /api/users/{id} 更新用户信息
商品模块
方法 路径 说明

GET /api/products 商品列表

GET /api/products/{id} 商品详情

POST /api/products 添加商品

PUT /api/products/{id} 更新商品

DELETE /api/products/{id} 删除商品
购物车模块
方法 路径 说明

GET /api/cart/user/{userId} 获取用户购物车

POST /api/cart/add 添加商品到购物车

PUT /api/cart/{id} 更新购物车商品数量

DELETE /api/cart/{id} 删除购物车商品

🔧 开发指南

导入项目到IDEA

1. 打开IntelliJ IDEA
2. 选择 File → Open
3. 选择项目根目录
4. 等待Maven依赖下载完成
5. 运行 EcommerceDemoApplication.java

打包部署

# 打包
mvn clean package -DskipTests

# 生成的jar包在target目录
java -jar target/ecommerce-demo-1.0.0.jar


📁 提交文件说明


ecommerce-platform-backend/
├── src/                          # 源代码
├── database/                     # 数据库文件
│   └── ecommerce_demo_backup.sql # 完整数据库备份
├── src/main/resources/          # 配置文件
│   ├── application.yml          # 主配置文件
│   └── static/                  # 静态资源
├── pom.xml                      # Maven配置
├── README.md                    # 项目说明
├── 开发环境说明.md               # 开发环境要求
├── 第三方资源使用声明.md         # 第三方库声明
└── 作品原创性声明.md             # 原创性声明


📋 开发计划进度

• ✅ 项目基础搭建

• ✅ 用户模块（注册/登录/个人中心）

• ✅ 商品模块（增删改查）

• ✅ 购物车模块

• 🔄 订单系统（开发中）

• ⬜ 支付集成

• ⬜ 评论与评价

• ⬜ 消息通知

👥 测试账号


管理员账号：
- 用户名：admin
- 密码：123456
- 邮箱：admin@njupt.edu.cn

普通用户（密码多为123456）：
- 用户名：user001 ~ user219
- 邮箱：对应用户名@njupt.edu.cn


🐛 常见问题

1. 端口占用：修改application.yml中的server.port
2. 数据库连接失败：检查MySQL服务是否启动
3. 依赖下载失败：检查Maven配置或使用阿里云镜像
4. 编码问题：确保数据库使用utf8mb4字符集
5. 网络错误：确保本机运行前后端才可使用localhost的url，否则需在CorsConfig、前端代码中添加url配置，详见CorsConfig


📄 许可证

本项目仅供学习交流使用，遵循MIT开源协议。

📞 联系方式

• 后端作者：邱恬晨

• 邮箱：3110696472@qq.com

• 学校：南京邮电大学

• 版本：v1.0.0

启动成功后，请访问 http://localhost:8081 查看欢迎页面