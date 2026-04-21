-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce_demo
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `achievement_tag`
--

DROP TABLE IF EXISTS `achievement_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement_tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签代码，如: LATE_NIGHT_FOODIE',
  `tag_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签显示名称，如: #深夜食神',
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签描述',
  `rule_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规则类型',
  `rule_config` json DEFAULT NULL COMMENT '规则配置(JSON格式)',
  `icon_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标URL',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_code` (`tag_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievement_tag`
--

LOCK TABLES `achievement_tag` WRITE;
/*!40000 ALTER TABLE `achievement_tag` DISABLE KEYS */;
INSERT INTO `achievement_tag` VALUES (1,'LATE_NIGHT_FOODIE','#深夜食神','晚上10点后下单',NULL,NULL,NULL,'2026-02-26 20:58:15'),(2,'BIG_SPENDER','#大户人家','单笔订单金额超过100元',NULL,NULL,NULL,'2026-02-26 20:58:15'),(3,'SPICY_LOVER','#无辣不欢','订单包含辣味商品',NULL,NULL,NULL,'2026-02-26 20:58:15'),(4,'FIRST_PURCHASE','#初尝鲜','首次购买某品类商品',NULL,NULL,NULL,'2026-02-26 20:58:15'),(5,'RAINY_DAY_ORDER','#风雨无阻','雨天坚持下单',NULL,NULL,NULL,'2026-02-26 20:58:15');
/*!40000 ALTER TABLE `achievement_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `selected` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否选中: 0-未选中, 1-选中',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `product_image` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品图片',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '商品总价(数量*单价)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_user_product` (`user_id`,`product_id`),
  KEY `idx_selected` (`selected`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (7,1,1,1,1,'2026-03-20 21:15:51','2026-03-20 21:15:51','珍珠奶茶',16.00,'/uploads/products/1.jpg',16.00),(8,1,2,1,1,'2026-03-20 21:15:59','2026-03-20 21:15:59','杨枝甘露',18.00,'/uploads/products/5.jpg',18.00),(10,11,1,1,1,'2026-03-26 23:33:43','2026-03-26 23:33:43','珍珠奶茶',16.00,'/uploads/products/1.jpg',16.00),(18,12,3,1,1,'2026-03-28 17:05:12','2026-03-28 17:05:12','芋泥奶绿',17.00,'/uploads/products/3.jpg',17.00),(19,12,2,1,1,'2026-03-28 17:05:26','2026-03-28 17:05:26','杨枝甘露',18.00,'/uploads/products/5.jpg',18.00),(20,12,1,1,1,'2026-03-28 17:07:36','2026-03-28 17:07:36','珍珠奶茶',16.00,'/uploads/products/1.jpg',16.00),(21,11,2,1,1,'2026-03-28 22:09:56','2026-03-28 22:09:56','杨枝甘露',18.00,'/uploads/products/5.jpg',18.00);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL COMMENT '类型:1-无门槛,2-满减',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '优惠券名称',
  `amount` decimal(10,2) NOT NULL COMMENT '优惠金额',
  `min_amount` decimal(10,2) DEFAULT '0.00' COMMENT '最低使用金额',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用范围描述',
  `can_receive_time` datetime NOT NULL COMMENT '可领取时间',
  `expire_days` int DEFAULT '30' COMMENT '领取后有效期天数',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,1,'新人无门槛券',10.00,0.00,'新人专享，无门槛立减10元','2024-01-01 00:00:00',30,'2026-03-16 20:30:53','2026-03-16 20:30:53'),(2,2,'通用满减券',5.00,50.00,'全平台通用，满50元可用','2026-03-20 00:00:00',30,'2026-03-16 20:30:53','2026-03-16 20:30:53');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-待付款, 2-待发货, 3-待收货, 4-已完成, 5-已取消',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `cancel_reason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消原因',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称（下单时的快照）',
  `product_price` decimal(10,2) NOT NULL COMMENT '商品单价（下单时的快照）',
  `quantity` int NOT NULL COMMENT '数量',
  `subtotal` decimal(10,2) NOT NULL COMMENT '小计金额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `category` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类',
  `image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片URL',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-上架, 0-下架',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `view_count` int DEFAULT '0',
  `collect_count` int DEFAULT '0',
  `purchase_count` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'珍珠奶茶','丝滑奶茶搭配Q弹黑糖珍珠，口感醇厚香甜，经典奶茶风味，每一口都充满浓郁奶香与嚼劲十足的珍珠。',16.00,150,'奶茶','/uploads/products/1.jpg',1,'2026-02-26 20:58:04','2026-04-03 16:41:22',1250,320,850),(2,'杨枝甘露','精选芒果果肉搭配西柚果粒与椰奶，清爽酸甜，果香浓郁，是夏日最受欢迎的经典水果奶茶。',18.00,120,'果茶','/uploads/products/5.jpg',1,'2026-02-26 20:58:04','2026-04-03 16:41:22',980,280,320),(3,'芋泥奶绿','绵密芋泥融合清香绿茶与香浓牛奶，口感细腻顺滑，芋香浓郁，甜而不腻。',17.00,100,'奶茶','/uploads/products/3.jpg',1,'2026-02-26 20:58:04','2026-04-03 16:41:22',890,240,280),(4,'抹茶拿铁','精选抹茶粉与醇香牛奶融合，茶香清新、奶味醇厚，层次丰富，口感丝滑。',20.00,80,'咖啡','/uploads/products/4.jpg',1,'2026-02-26 20:58:04','2026-04-03 16:41:22',760,190,220),(5,'多肉葡萄','整颗青提铺顶，紫红果肉冰沙打底，底部藏着脆波波和果冻块。一口爆汁，酸甜平衡，仿佛把果园装进杯子里！',18.00,130,'果茶','/uploads/products/2.jpg',1,'2026-02-26 20:58:04','2026-04-03 16:41:22',650,150,180);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456','admin@njupt.edu.cn','2026-03-07 11:49:17','2026-03-07 11:49:17'),(3,'coder','123456','coder@njupt.edu.cn','2026-03-07 11:49:17','2026-03-07 11:49:17'),(4,'xiaomei','123456','xiaomei@njupt.edu.cn','2026-03-07 11:49:17','2026-03-07 11:49:17'),(5,'techgeek','123456','tech@njupt.edu.cn','2026-03-07 11:49:17','2026-03-07 11:49:17'),(6,'Aaron','1234qtc5','3333333333@qq.com','2026-03-21 20:35:20','2026-03-22 09:59:33'),(7,'coco','1234coco','22@qq.com','2026-03-22 12:34:18','2026-03-22 12:34:18'),(8,'stomaster','1234567',NULL,'2026-03-24 22:34:17','2026-03-24 22:34:17'),(9,'QQBB','123456','1111@qq.com','2026-03-26 22:47:51','2026-03-26 22:47:51'),(10,'xuan','987654321a',NULL,'2026-03-26 23:27:38','2026-03-26 23:27:38'),(11,'xuanb','987654321a',NULL,'2026-03-26 23:33:26','2026-03-26 23:33:26'),(12,'xixi','xixixi',NULL,'2026-03-28 13:34:10','2026-03-28 13:34:10'),(13,'kkkkk','kkkkkk',NULL,'2026-03-29 19:58:16','2026-03-29 19:58:16'),(15,'user001','123456','user001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(16,'user002','123456','user002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(17,'user003','123456','user003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(18,'user004','123456','user004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(19,'user005','123456','user005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(20,'alice001','123456','alice001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(21,'alice002','123456','alice002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(22,'alice003','123456','alice003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(23,'alice004','123456','alice004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(24,'alice005','123456','alice005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(25,'bob001','123456','bob001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(26,'bob002','123456','bob002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(27,'bob003','123456','bob003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(28,'bob004','123456','bob004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(29,'bob005','123456','bob005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(30,'charlie001','123456','charlie001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(31,'charlie002','123456','charlie002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(32,'charlie003','123456','charlie003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(33,'charlie004','123456','charlie004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(34,'charlie005','123456','charlie005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(35,'david001','123456','david001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(36,'david002','123456','david002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(37,'david003','123456','david003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(38,'david004','123456','david004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(39,'david005','123456','david005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(40,'eva001','123456','eva001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(41,'eva002','123456','eva002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(42,'eva003','123456','eva003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(43,'eva004','123456','eva004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(44,'eva005','123456','eva005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(45,'frank001','123456','frank001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(46,'frank002','123456','frank002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(47,'frank003','123456','frank003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(48,'frank004','123456','frank004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(49,'frank005','123456','frank005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(50,'grace001','123456','grace001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(51,'grace002','123456','grace002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(52,'grace003','123456','grace003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(53,'grace004','123456','grace004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(54,'grace005','123456','grace005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(55,'henry001','123456','henry001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(56,'henry002','123456','henry002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(57,'henry003','123456','henry003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(58,'henry004','123456','henry004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(59,'henry005','123456','henry005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(60,'ivy001','123456','ivy001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(61,'ivy002','123456','ivy002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(62,'ivy003','123456','ivy003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(63,'ivy004','123456','ivy004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(64,'ivy005','123456','ivy005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(65,'jack001','123456','jack001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(66,'jack002','123456','jack002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(67,'jack003','123456','jack003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(68,'jack004','123456','jack004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(69,'jack005','123456','jack005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(70,'kate001','123456','kate001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(71,'kate002','123456','kate002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(72,'kate003','123456','kate003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(73,'kate004','123456','kate004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(74,'kate005','123456','kate005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(75,'leo001','123456','leo001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(76,'leo002','123456','leo002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(77,'leo003','123456','leo003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(78,'leo004','123456','leo004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(79,'leo005','123456','leo005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(80,'mia001','123456','mia001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(81,'mia002','123456','mia002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(82,'mia003','123456','mia003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(83,'mia004','123456','mia004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(84,'mia005','123456','mia005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(85,'noah001','123456','noah001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(86,'noah002','123456','noah002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(87,'noah003','123456','noah003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(88,'noah004','123456','noah004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(89,'noah005','123456','noah005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(90,'olivia001','123456','olivia001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(91,'olivia002','123456','olivia002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(92,'olivia003','123456','olivia003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(93,'olivia004','123456','olivia004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(94,'olivia005','123456','olivia005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(95,'peter001','123456','peter001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(96,'peter002','123456','peter002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(97,'peter003','123456','peter003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(98,'peter004','123456','peter004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(99,'peter005','123456','peter005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(100,'queen001','123456','queen001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(101,'queen002','123456','queen002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(102,'queen003','123456','queen003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(103,'queen004','123456','queen004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(104,'queen005','123456','queen005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(105,'ryan001','123456','ryan001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(106,'ryan002','123456','ryan002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(107,'ryan003','123456','ryan003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(108,'ryan004','123456','ryan004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(109,'ryan005','123456','ryan005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(110,'sophia001','123456','sophia001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(111,'sophia002','123456','sophia002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(112,'sophia003','123456','sophia003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(113,'sophia004','123456','sophia004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(114,'sophia005','123456','sophia005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(115,'tom001','123456','tom001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(116,'tom002','123456','tom002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(117,'tom003','123456','tom003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(118,'tom004','123456','tom004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(119,'tom005','123456','tom005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(120,'uma001','123456','uma001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(121,'uma002','123456','uma002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(122,'uma003','123456','uma003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(123,'uma004','123456','uma004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(124,'uma005','123456','uma005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(125,'victor001','123456','victor001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(126,'victor002','123456','victor002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(127,'victor003','123456','victor003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(128,'victor004','123456','victor004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(129,'victor005','123456','victor005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(130,'wendy001','123456','wendy001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(131,'wendy002','123456','wendy002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(132,'wendy003','123456','wendy003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(133,'wendy004','123456','wendy004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(134,'wendy005','123456','wendy005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(135,'xander001','123456','xander001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(136,'xander002','123456','xander002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(137,'xander003','123456','xander003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(138,'xander004','123456','xander004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(139,'xander005','123456','xander005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(140,'yara001','123456','yara001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(141,'yara002','123456','yara002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(142,'yara003','123456','yara003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(143,'yara004','123456','yara004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(144,'yara005','123456','yara005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(145,'zoe001','123456','zoe001@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(146,'zoe002','123456','zoe002@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(147,'zoe003','123456','zoe003@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(148,'zoe004','123456','zoe004@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(149,'zoe005','123456','zoe005@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(150,'test101','123456','test101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(151,'test102','123456','test102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(152,'test103','123456','test103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(153,'test104','123456','test104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(154,'test105','123456','test105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(155,'demo101','123456','demo101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(156,'demo102','123456','demo102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(157,'demo103','123456','demo103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(158,'demo104','123456','demo104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(159,'demo105','123456','demo105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(160,'student101','123456','student101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(161,'student102','123456','student102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(162,'student103','123456','student103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(163,'student104','123456','student104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(164,'student105','123456','student105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(165,'teacher101','123456','teacher101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(166,'teacher102','123456','teacher102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(167,'teacher103','123456','teacher103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(168,'teacher104','123456','teacher104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(169,'teacher105','123456','teacher105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(170,'developer101','123456','developer101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(171,'developer102','123456','developer102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(172,'developer103','123456','developer103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(173,'developer104','123456','developer104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(174,'developer105','123456','developer105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(175,'manager101','123456','manager101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(176,'manager102','123456','manager102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(177,'manager103','123456','manager103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(178,'manager104','123456','manager104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(179,'manager105','123456','manager105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(180,'guest101','123456','guest101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(181,'guest102','123456','guest102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(182,'guest103','123456','guest103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(183,'guest104','123456','guest104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(184,'guest105','123456','guest105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(185,'member101','123456','member101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(186,'member102','123456','member102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(187,'member103','123456','member103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(188,'member104','123456','member104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(189,'member105','123456','member105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(190,'vip101','123456','vip101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(191,'vip102','123456','vip102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(192,'vip103','123456','vip103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(193,'vip104','123456','vip104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(194,'vip105','123456','vip105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(195,'customer101','123456','customer101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(196,'customer102','123456','customer102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(197,'customer103','123456','customer103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(198,'customer104','123456','customer104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(199,'customer105','123456','customer105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(200,'buyer101','123456','buyer101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(201,'buyer102','123456','buyer102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(202,'buyer103','123456','buyer103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(203,'buyer104','123456','buyer104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(204,'buyer105','123456','buyer105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(205,'seller101','123456','seller101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(206,'seller102','123456','seller102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(207,'seller103','123456','seller103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(208,'seller104','123456','seller104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(209,'seller105','123456','seller105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(210,'admin101','123456','admin101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(211,'admin102','123456','admin102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(212,'admin103','123456','admin103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(213,'admin104','123456','admin104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(214,'admin105','123456','admin105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(215,'super101','123456','super101@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(216,'super102','123456','super102@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(217,'super103','123456','super103@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(218,'super104','123456','super104@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(219,'super105','123456','super105@njupt.edu.cn','2026-04-01 18:51:44','2026-04-01 18:51:44'),(220,'Lynn','123456','B26060603@njupt.edu.cn','2026-04-03 17:03:01','2026-04-03 17:03:01');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_collection`
--

DROP TABLE IF EXISTS `user_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_image` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `product_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收藏时的商品名称',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '收藏时的商品价格',
  `product_category` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收藏时的商品分类',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  CONSTRAINT `user_collection_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collection`
--

LOCK TABLES `user_collection` WRITE;
/*!40000 ALTER TABLE `user_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_coupon`
--

DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` int NOT NULL COMMENT '优惠券ID',
  `status` tinyint DEFAULT '1' COMMENT '状态:1-未使用 2-已使用 3-已过期',
  `receive_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT NULL COMMENT '使用订单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_coupon` (`user_id`,`coupon_id`) COMMENT '用户每种券只能领一次',
  KEY `idx_user_id` (`user_id`),
  KEY `idx_coupon_id` (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_coupon`
--

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
INSERT INTO `user_coupon` VALUES (4,1,1,1,'2026-03-17 19:02:17','2026-04-16 19:02:17',NULL,NULL);
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `avatar_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `signature` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `view_count` int DEFAULT '0',
  `collect_count` int DEFAULT '0',
  `purchase_count` int DEFAULT '0',
  `grade` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `school` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `college` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `student_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tags` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `user_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1,1,NULL,'admin','admin的个人主页',396,29,2,'大四','南京邮电大学','计算机科学与技术学院','B202101001','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(2,3,NULL,'coder','coder的个人主页',1090,25,18,'大三','南京邮电大学','网络空间安全学院','B201901003','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(3,4,NULL,'xiaomei','xiaomei的个人主页',970,7,4,'大二','南京邮电大学','社会与人口学院','B201901004','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(4,5,NULL,'techgeek','techgeek的个人主页',738,47,24,'大二','南京邮电大学','社会与人口学院','B201901005','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(5,6,NULL,'Aaron','Aaron的个人主页',654,48,5,'大一','南京邮电大学','现代邮政学院','B201901006','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(6,7,NULL,'coco','coco的个人主页',642,35,26,'大二','南京邮电大学','社会与人口学院','B202101007','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(7,8,NULL,'stomaster','stomaster的个人主页',977,5,27,'大二','南京邮电大学','社会与人口学院','B201901008','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(8,9,NULL,'QQBB','QQBB的个人主页',805,7,20,'研一','南京邮电大学','网络空间安全学院','B201901009','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(9,10,NULL,'xuan','xuan的个人主页',1037,40,7,'大四','南京邮电大学','集成电路与微电子学院','B202101010','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(10,11,NULL,'xuanb','xuanb的个人主页',956,39,11,'大三','南京邮电大学','社会与人口学院','B202101011','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(11,12,NULL,'xixi','xixi的个人主页',687,40,7,'大四','南京邮电大学','计算机科学与技术学院','B202101012','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(12,13,NULL,'kkkkk','kkkkk的个人主页',639,4,25,'大一','南京邮电大学','现代邮政学院','B202101013','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(13,15,NULL,'user001','user001的个人主页',517,12,29,'大一','南京邮电大学','集成电路与微电子学院','B202101015','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(14,16,NULL,'user002','user002的个人主页',703,37,26,'大二','南京邮电大学','网络空间安全学院','B202101016','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(15,17,NULL,'user003','user003的个人主页',696,2,13,'大一','南京邮电大学','集成电路与微电子学院','B202001017','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(16,18,NULL,'user004','user004的个人主页',931,28,11,'大二','南京邮电大学','集成电路与微电子学院','B201901018','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(17,19,NULL,'user005','user005的个人主页',907,4,0,'大四','南京邮电大学','社会与人口学院','B202101019','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(18,20,NULL,'alice001','alice001的个人主页',283,49,11,'研一','南京邮电大学','网络空间安全学院','B202001020','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(19,21,NULL,'alice002','alice002的个人主页',258,20,17,'大四','南京邮电大学','网络空间安全学院','B202001021','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(20,22,NULL,'alice003','alice003的个人主页',797,38,22,'大三','南京邮电大学','社会与人口学院','B202101022','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(21,23,NULL,'alice004','alice004的个人主页',142,0,27,'大三','南京邮电大学','社会与人口学院','B201901023','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(22,24,NULL,'alice005','alice005的个人主页',640,40,13,'大四','南京邮电大学','网络空间安全学院','B202001024','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(23,25,NULL,'bob001','bob001的个人主页',912,26,7,'大三','南京邮电大学','人工智能学院','B202101025','网红奶茶,打卡达人,芋泥波波','2026-04-03 16:23:32','2026-04-03 16:23:32'),(24,26,NULL,'bob002','bob002的个人主页',1015,26,25,'大四','南京邮电大学','人工智能学院','B202101026','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(25,27,NULL,'bob003','bob003的个人主页',611,12,20,'大四','南京邮电大学','软件工程学院','B201901027','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(26,28,NULL,'bob004','bob004的个人主页',134,36,15,'大三','南京邮电大学','现代邮政学院','B202101028','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(27,29,NULL,'bob005','bob005的个人主页',1073,3,12,'研一','南京邮电大学','集成电路与微电子学院','B201901029','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(28,30,NULL,'charlie001','charlie001的个人主页',859,46,12,'大二','南京邮电大学','人工智能学院','B202101030','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(29,31,NULL,'charlie002','charlie002的个人主页',1021,14,20,'大三','南京邮电大学','社会与人口学院','B201901031','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(30,32,NULL,'charlie003','charlie003的个人主页',776,20,1,'研一','南京邮电大学','社会与人口学院','B201901032','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(31,33,NULL,'charlie004','charlie004的个人主页',170,40,26,'研一','南京邮电大学','人工智能学院','B201901033','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(32,34,NULL,'charlie005','charlie005的个人主页',243,25,3,'大一','南京邮电大学','集成电路与微电子学院','B201901034','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(33,35,NULL,'david001','david001的个人主页',967,25,28,'大二','南京邮电大学','软件工程学院','B201901035','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(34,36,NULL,'david002','david002的个人主页',770,19,27,'大二','南京邮电大学','人工智能学院','B201901036','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(35,37,NULL,'david003','david003的个人主页',862,39,20,'研一','南京邮电大学','集成电路与微电子学院','B202001037','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(36,38,NULL,'david004','david004的个人主页',889,18,13,'大一','南京邮电大学','软件工程学院','B202001038','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(37,39,NULL,'david005','david005的个人主页',262,6,3,'大二','南京邮电大学','社会与人口学院','B202101039','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(38,40,NULL,'eva001','eva001的个人主页',224,41,24,'大三','南京邮电大学','网络空间安全学院','B202101040','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(39,41,NULL,'eva002','eva002的个人主页',742,19,1,'大一','南京邮电大学','软件工程学院','B202101041','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(40,42,NULL,'eva003','eva003的个人主页',993,10,10,'大一','南京邮电大学','现代邮政学院','B202101042','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(41,43,NULL,'eva004','eva004的个人主页',928,16,3,'大四','南京邮电大学','社会与人口学院','B202101043','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(42,44,NULL,'eva005','eva005的个人主页',850,42,1,'大四','南京邮电大学','人工智能学院','B202001044','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(43,45,NULL,'frank001','frank001的个人主页',339,20,9,'大二','南京邮电大学','社会与人口学院','B202101045','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(44,46,NULL,'frank002','frank002的个人主页',1040,1,8,'大二','南京邮电大学','集成电路与微电子学院','B201901046','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(45,47,NULL,'frank003','frank003的个人主页',158,48,20,'大三','南京邮电大学','现代邮政学院','B202001047','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(46,48,NULL,'frank004','frank004的个人主页',807,45,12,'大二','南京邮电大学','网络空间安全学院','B202001048','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(47,49,NULL,'frank005','frank005的个人主页',422,36,19,'大一','南京邮电大学','计算机科学与技术学院','B202101049','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(48,50,NULL,'grace001','grace001的个人主页',207,21,23,'大四','南京邮电大学','人工智能学院','B201901050','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(49,51,NULL,'grace002','grace002的个人主页',738,12,10,'大一','南京邮电大学','人工智能学院','B201901051','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(50,52,NULL,'grace003','grace003的个人主页',404,29,2,'大四','南京邮电大学','集成电路与微电子学院','B202001052','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(51,53,NULL,'grace004','grace004的个人主页',262,34,27,'大三','南京邮电大学','人工智能学院','B202101053','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(52,54,NULL,'grace005','grace005的个人主页',674,36,28,'大三','南京邮电大学','集成电路与微电子学院','B201901054','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(53,55,NULL,'henry001','henry001的个人主页',678,24,21,'大一','南京邮电大学','现代邮政学院','B202101055','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(54,56,NULL,'henry002','henry002的个人主页',550,13,0,'大一','南京邮电大学','集成电路与微电子学院','B201901056','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(55,57,NULL,'henry003','henry003的个人主页',1037,49,3,'大四','南京邮电大学','现代邮政学院','B202101057','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(56,58,NULL,'henry004','henry004的个人主页',375,45,21,'研一','南京邮电大学','人工智能学院','B202101058','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(57,59,NULL,'henry005','henry005的个人主页',466,25,12,'大四','南京邮电大学','集成电路与微电子学院','B202101059','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(58,60,NULL,'ivy001','ivy001的个人主页',1076,19,0,'研一','南京邮电大学','现代邮政学院','B201901060','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(59,61,NULL,'ivy002','ivy002的个人主页',679,38,4,'大三','南京邮电大学','社会与人口学院','B202001061','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(60,62,NULL,'ivy003','ivy003的个人主页',633,2,18,'研一','南京邮电大学','现代邮政学院','B202101062','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(61,63,NULL,'ivy004','ivy004的个人主页',1092,36,19,'大一','南京邮电大学','现代邮政学院','B202101063','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(62,64,NULL,'ivy005','ivy005的个人主页',438,46,17,'大一','南京邮电大学','计算机科学与技术学院','B202001064','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(63,65,NULL,'jack001','jack001的个人主页',554,46,7,'大三','南京邮电大学','现代邮政学院','B202101065','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(64,66,NULL,'jack002','jack002的个人主页',830,14,7,'大二','南京邮电大学','集成电路与微电子学院','B202101066','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(65,67,NULL,'jack003','jack003的个人主页',977,32,17,'大一','南京邮电大学','软件工程学院','B202101067','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(66,68,NULL,'jack004','jack004的个人主页',603,10,16,'大一','南京邮电大学','计算机科学与技术学院','B202001068','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(67,69,NULL,'jack005','jack005的个人主页',1095,13,10,'研一','南京邮电大学','现代邮政学院','B202001069','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(68,70,NULL,'kate001','kate001的个人主页',954,13,23,'大一','南京邮电大学','计算机科学与技术学院','B202101070','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(69,71,NULL,'kate002','kate002的个人主页',165,7,17,'大二','南京邮电大学','计算机科学与技术学院','B202101071','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(70,72,NULL,'kate003','kate003的个人主页',667,14,21,'大四','南京邮电大学','网络空间安全学院','B202001072','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(71,73,NULL,'kate004','kate004的个人主页',1054,4,17,'大四','南京邮电大学','软件工程学院','B202101073','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(72,74,NULL,'kate005','kate005的个人主页',479,13,7,'大二','南京邮电大学','人工智能学院','B201901074','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(73,75,NULL,'leo001','leo001的个人主页',850,26,11,'大二','南京邮电大学','计算机科学与技术学院','B202001075','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(74,76,NULL,'leo002','leo002的个人主页',650,28,6,'大二','南京邮电大学','人工智能学院','B202001076','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(75,77,NULL,'leo003','leo003的个人主页',988,32,17,'研一','南京邮电大学','人工智能学院','B202001077','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(76,78,NULL,'leo004','leo004的个人主页',870,34,5,'研一','南京邮电大学','网络空间安全学院','B202101078','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(77,79,NULL,'leo005','leo005的个人主页',624,41,16,'大二','南京邮电大学','人工智能学院','B202101079','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(78,80,NULL,'mia001','mia001的个人主页',1009,30,9,'大四','南京邮电大学','网络空间安全学院','B202001080','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(79,81,NULL,'mia002','mia002的个人主页',812,35,11,'研一','南京邮电大学','集成电路与微电子学院','B201901081','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(80,82,NULL,'mia003','mia003的个人主页',772,43,11,'大二','南京邮电大学','人工智能学院','B202101082','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(81,83,NULL,'mia004','mia004的个人主页',491,12,1,'大三','南京邮电大学','现代邮政学院','B202001083','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(82,84,NULL,'mia005','mia005的个人主页',1064,14,17,'大一','南京邮电大学','现代邮政学院','B201901084','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(83,85,NULL,'noah001','noah001的个人主页',440,39,27,'大二','南京邮电大学','软件工程学院','B202101085','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(84,86,NULL,'noah002','noah002的个人主页',261,39,14,'大一','南京邮电大学','社会与人口学院','B202001086','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(85,87,NULL,'noah003','noah003的个人主页',333,21,13,'研一','南京邮电大学','人工智能学院','B201901087','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(86,88,NULL,'noah004','noah004的个人主页',463,38,22,'大二','南京邮电大学','社会与人口学院','B202001088','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(87,89,NULL,'noah005','noah005的个人主页',106,41,3,'大一','南京邮电大学','集成电路与微电子学院','B202001089','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(88,90,NULL,'olivia001','olivia001的个人主页',999,4,21,'大二','南京邮电大学','计算机科学与技术学院','B202001090','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(89,91,NULL,'olivia002','olivia002的个人主页',681,20,8,'大一','南京邮电大学','集成电路与微电子学院','B201901091','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(90,92,NULL,'olivia003','olivia003的个人主页',549,11,23,'大二','南京邮电大学','社会与人口学院','B202101092','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(91,93,NULL,'olivia004','olivia004的个人主页',864,29,20,'大四','南京邮电大学','计算机科学与技术学院','B202001093','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(92,94,NULL,'olivia005','olivia005的个人主页',490,30,26,'大四','南京邮电大学','网络空间安全学院','B202001094','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(93,95,NULL,'peter001','peter001的个人主页',456,15,14,'大三','南京邮电大学','社会与人口学院','B202001095','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(94,96,NULL,'peter002','peter002的个人主页',1049,11,10,'研一','南京邮电大学','社会与人口学院','B201901096','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(95,97,NULL,'peter003','peter003的个人主页',137,15,12,'大二','南京邮电大学','社会与人口学院','B202001097','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(96,98,NULL,'peter004','peter004的个人主页',119,36,18,'研一','南京邮电大学','软件工程学院','B202001098','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(97,99,NULL,'peter005','peter005的个人主页',787,22,4,'大二','南京邮电大学','网络空间安全学院','B202001099','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(98,100,NULL,'queen001','queen001的个人主页',993,41,15,'大一','南京邮电大学','网络空间安全学院','B202001100','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(99,101,NULL,'queen002','queen002的个人主页',107,14,12,'大一','南京邮电大学','社会与人口学院','B201901101','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(100,102,NULL,'queen003','queen003的个人主页',351,0,9,'大三','南京邮电大学','现代邮政学院','B202101102','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(101,103,NULL,'queen004','queen004的个人主页',670,38,4,'大三','南京邮电大学','社会与人口学院','B202101103','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(102,104,NULL,'queen005','queen005的个人主页',927,25,3,'大一','南京邮电大学','社会与人口学院','B202101104','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(103,105,NULL,'ryan001','ryan001的个人主页',665,39,6,'大四','南京邮电大学','计算机科学与技术学院','B202101105','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(104,106,NULL,'ryan002','ryan002的个人主页',390,36,22,'大四','南京邮电大学','集成电路与微电子学院','B202001106','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(105,107,NULL,'ryan003','ryan003的个人主页',911,23,27,'大一','南京邮电大学','集成电路与微电子学院','B202001107','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(106,108,NULL,'ryan004','ryan004的个人主页',362,32,14,'大二','南京邮电大学','网络空间安全学院','B202001108','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(107,109,NULL,'ryan005','ryan005的个人主页',468,9,25,'大四','南京邮电大学','网络空间安全学院','B202101109','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(108,110,NULL,'sophia001','sophia001的个人主页',759,49,27,'大四','南京邮电大学','现代邮政学院','B202101110','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(109,111,NULL,'sophia002','sophia002的个人主页',371,12,14,'大三','南京邮电大学','网络空间安全学院','B202101111','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(110,112,NULL,'sophia003','sophia003的个人主页',842,41,28,'大二','南京邮电大学','现代邮政学院','B202001112','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(111,113,NULL,'sophia004','sophia004的个人主页',1058,18,0,'研一','南京邮电大学','社会与人口学院','B201901113','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(112,114,NULL,'sophia005','sophia005的个人主页',583,15,2,'大三','南京邮电大学','计算机科学与技术学院','B202101114','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(113,115,NULL,'tom001','tom001的个人主页',357,46,26,'大四','南京邮电大学','现代邮政学院','B201901115','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(114,116,NULL,'tom002','tom002的个人主页',1032,47,27,'大四','南京邮电大学','社会与人口学院','B202101116','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(115,117,NULL,'tom003','tom003的个人主页',270,34,27,'大三','南京邮电大学','集成电路与微电子学院','B202101117','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(116,118,NULL,'tom004','tom004的个人主页',366,32,14,'大三','南京邮电大学','集成电路与微电子学院','B202101118','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(117,119,NULL,'tom005','tom005的个人主页',923,20,18,'大四','南京邮电大学','集成电路与微电子学院','B202101119','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(118,120,NULL,'uma001','uma001的个人主页',851,0,25,'大一','南京邮电大学','计算机科学与技术学院','B202001120','网红奶茶,打卡达人,芋泥波波','2026-04-03 16:23:32','2026-04-03 16:23:32'),(119,121,NULL,'uma002','uma002的个人主页',833,28,19,'大三','南京邮电大学','集成电路与微电子学院','B202101121','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(120,122,NULL,'uma003','uma003的个人主页',537,21,26,'大一','南京邮电大学','现代邮政学院','B201901122','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(121,123,NULL,'uma004','uma004的个人主页',377,27,27,'研一','南京邮电大学','集成电路与微电子学院','B202101123','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(122,124,NULL,'uma005','uma005的个人主页',905,30,18,'大二','南京邮电大学','网络空间安全学院','B202001124','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(123,125,NULL,'victor001','victor001的个人主页',1042,18,1,'大一','南京邮电大学','软件工程学院','B201901125','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(124,126,NULL,'victor002','victor002的个人主页',165,26,13,'大四','南京邮电大学','计算机科学与技术学院','B202101126','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(125,127,NULL,'victor003','victor003的个人主页',739,44,16,'大一','南京邮电大学','社会与人口学院','B202001127','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(126,128,NULL,'victor004','victor004的个人主页',295,10,15,'研一','南京邮电大学','集成电路与微电子学院','B201901128','热奶茶,暖心饮品,全糖热饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(127,129,NULL,'victor005','victor005的个人主页',392,47,27,'大四','南京邮电大学','现代邮政学院','B202101129','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(128,130,NULL,'wendy001','wendy001的个人主页',1015,36,28,'大三','南京邮电大学','网络空间安全学院','B201901130','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(129,131,NULL,'wendy002','wendy002的个人主页',961,9,12,'大三','南京邮电大学','计算机科学与技术学院','B202001131','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(130,132,NULL,'wendy003','wendy003的个人主页',281,9,10,'大二','南京邮电大学','软件工程学院','B202101132','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(131,133,NULL,'wendy004','wendy004的个人主页',1046,9,3,'大一','南京邮电大学','现代邮政学院','B201901133','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(132,134,NULL,'wendy005','wendy005的个人主页',1094,10,1,'大四','南京邮电大学','人工智能学院','B202001134','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(133,135,NULL,'xander001','xander001的个人主页',829,14,8,'大三','南京邮电大学','社会与人口学院','B202001135','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(134,136,NULL,'xander002','xander002的个人主页',333,3,19,'研一','南京邮电大学','人工智能学院','B201901136','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(135,137,NULL,'xander003','xander003的个人主页',821,36,14,'大二','南京邮电大学','网络空间安全学院','B201901137','热奶茶,暖心饮品,全糖热饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(136,138,NULL,'xander004','xander004的个人主页',961,37,5,'大三','南京邮电大学','网络空间安全学院','B202001138','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(137,139,NULL,'xander005','xander005的个人主页',1009,31,12,'大一','南京邮电大学','现代邮政学院','B202001139','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(138,140,NULL,'yara001','yara001的个人主页',232,38,14,'大一','南京邮电大学','现代邮政学院','B202001140','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(139,141,NULL,'yara002','yara002的个人主页',697,39,4,'大三','南京邮电大学','现代邮政学院','B201901141','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(140,142,NULL,'yara003','yara003的个人主页',167,10,25,'大四','南京邮电大学','社会与人口学院','B202101142','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(141,143,NULL,'yara004','yara004的个人主页',890,18,15,'大三','南京邮电大学','现代邮政学院','B201901143','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(142,144,NULL,'yara005','yara005的个人主页',186,18,18,'研一','南京邮电大学','集成电路与微电子学院','B202101144','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(143,145,NULL,'zoe001','zoe001的个人主页',623,23,22,'大二','南京邮电大学','网络空间安全学院','B202001145','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(144,146,NULL,'zoe002','zoe002的个人主页',271,38,9,'大二','南京邮电大学','社会与人口学院','B202001146','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(145,147,NULL,'zoe003','zoe003的个人主页',969,24,25,'研一','南京邮电大学','网络空间安全学院','B202101147','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(146,148,NULL,'zoe004','zoe004的个人主页',864,27,13,'大四','南京邮电大学','现代邮政学院','B202101148','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(147,149,NULL,'zoe005','zoe005的个人主页',325,34,23,'大四','南京邮电大学','现代邮政学院','B202101149','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(148,150,NULL,'test101','test101的个人主页',679,28,2,'大四','南京邮电大学','软件工程学院','B202001150','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(149,151,NULL,'test102','test102的个人主页',1012,26,26,'研一','南京邮电大学','社会与人口学院','B201901151','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(150,152,NULL,'test103','test103的个人主页',337,26,29,'大二','南京邮电大学','网络空间安全学院','B202001152','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(151,153,NULL,'test104','test104的个人主页',877,37,11,'大四','南京邮电大学','人工智能学院','B202001153','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(152,154,NULL,'test105','test105的个人主页',180,25,9,'大一','南京邮电大学','软件工程学院','B202001154','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(153,155,NULL,'demo101','demo101的个人主页',755,35,17,'研一','南京邮电大学','网络空间安全学院','B202001155','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(154,156,NULL,'demo102','demo102的个人主页',982,25,25,'大四','南京邮电大学','软件工程学院','B201901156','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(155,157,NULL,'demo103','demo103的个人主页',664,2,17,'大四','南京邮电大学','社会与人口学院','B202101157','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(156,158,NULL,'demo104','demo104的个人主页',648,20,10,'大四','南京邮电大学','人工智能学院','B201901158','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(157,159,NULL,'demo105','demo105的个人主页',701,27,27,'研一','南京邮电大学','计算机科学与技术学院','B202101159','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(158,160,NULL,'student101','student101的个人主页',131,31,2,'大三','南京邮电大学','软件工程学院','B202001160','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(159,161,NULL,'student102','student102的个人主页',997,16,27,'大四','南京邮电大学','网络空间安全学院','B201901161','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(160,162,NULL,'student103','student103的个人主页',314,18,6,'大一','南京邮电大学','软件工程学院','B202101162','玫瑰奶茶,优雅茶饮,低卡糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(161,163,NULL,'student104','student104的个人主页',111,1,3,'大三','南京邮电大学','社会与人口学院','B202101163','三分糖去冰,水果茶,健身茶饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(162,164,NULL,'student105','student105的个人主页',645,27,3,'研一','南京邮电大学','计算机科学与技术学院','B202001164','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(163,165,NULL,'teacher101','teacher101的个人主页',663,2,16,'大三','南京邮电大学','计算机科学与技术学院','B202001165','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(164,166,NULL,'teacher102','teacher102的个人主页',994,43,20,'大四','南京邮电大学','现代邮政学院','B201901166','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(165,167,NULL,'teacher103','teacher103的个人主页',627,45,0,'大二','南京邮电大学','网络空间安全学院','B202101167','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(166,168,NULL,'teacher104','teacher104的个人主页',179,46,11,'大二','南京邮电大学','社会与人口学院','B202001168','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(167,169,NULL,'teacher105','teacher105的个人主页',840,42,1,'大四','南京邮电大学','网络空间安全学院','B201901169','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(168,170,NULL,'developer101','developer101的个人主页',824,18,20,'大二','南京邮电大学','计算机科学与技术学院','B201901170','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(169,171,NULL,'developer102','developer102的个人主页',386,32,10,'研一','南京邮电大学','人工智能学院','B202101171','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(170,172,NULL,'developer103','developer103的个人主页',571,23,26,'大一','南京邮电大学','社会与人口学院','B202101172','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(171,173,NULL,'developer104','developer104的个人主页',953,43,22,'大一','南京邮电大学','现代邮政学院','B202101173','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(172,174,NULL,'developer105','developer105的个人主页',1028,5,21,'大二','南京邮电大学','网络空间安全学院','B201901174','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(173,175,NULL,'manager101','manager101的个人主页',444,30,1,'大二','南京邮电大学','现代邮政学院','B201901175','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(174,176,NULL,'manager102','manager102的个人主页',666,17,0,'大一','南京邮电大学','计算机科学与技术学院','B202101176','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(175,177,NULL,'manager103','manager103的个人主页',769,5,17,'大三','南京邮电大学','社会与人口学院','B202001177','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(176,178,NULL,'manager104','manager104的个人主页',261,19,14,'大二','南京邮电大学','现代邮政学院','B201901178','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(177,179,NULL,'manager105','manager105的个人主页',351,36,26,'大二','南京邮电大学','社会与人口学院','B201901179','热奶茶,暖心饮品,全糖热饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(178,180,NULL,'guest101','guest101的个人主页',162,35,11,'大四','南京邮电大学','现代邮政学院','B201901180','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(179,181,NULL,'guest102','guest102的个人主页',889,16,7,'大二','南京邮电大学','网络空间安全学院','B201901181','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(180,182,NULL,'guest103','guest103的个人主页',199,9,20,'研一','南京邮电大学','计算机科学与技术学院','B202001182','奶茶测评师,新品必尝,黑糖珍珠','2026-04-03 16:23:32','2026-04-03 16:23:32'),(181,183,NULL,'guest104','guest104的个人主页',159,31,28,'研一','南京邮电大学','社会与人口学院','B201901183','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(182,184,NULL,'guest105','guest105的个人主页',958,12,21,'大四','南京邮电大学','社会与人口学院','B202101184','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(183,185,NULL,'member101','member101的个人主页',499,14,6,'大二','南京邮电大学','网络空间安全学院','B201901185','网红奶茶,打卡达人,芋泥波波','2026-04-03 16:23:32','2026-04-03 16:23:32'),(184,186,NULL,'member102','member102的个人主页',1016,21,11,'大四','南京邮电大学','人工智能学院','B201901186','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(185,187,NULL,'member103','member103的个人主页',663,48,5,'研一','南京邮电大学','网络空间安全学院','B201901187','猫咪主题店,宠物友好,奶盖爱好者','2026-04-03 16:23:32','2026-04-03 16:23:32'),(186,188,NULL,'member104','member104的个人主页',308,35,28,'大四','南京邮电大学','软件工程学院','B202101188','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(187,189,NULL,'member105','member105的个人主页',239,45,3,'研一','南京邮电大学','集成电路与微电子学院','B202001189','花草茶,清新口味,低糖养生','2026-04-03 16:23:32','2026-04-03 16:23:32'),(188,190,NULL,'vip101','vip101的个人主页',109,29,28,'研一','南京邮电大学','社会与人口学院','B202101190','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(189,191,NULL,'vip102','vip102的个人主页',569,14,2,'大三','南京邮电大学','计算机科学与技术学院','B202101191','网红奶茶,打卡达人,芋泥波波','2026-04-03 16:23:32','2026-04-03 16:23:32'),(190,192,NULL,'vip103','vip103的个人主页',467,35,14,'大二','南京邮电大学','现代邮政学院','B202001192','网红奶茶,打卡达人,芋泥波波','2026-04-03 16:23:32','2026-04-03 16:23:32'),(191,193,NULL,'vip104','vip104的个人主页',658,33,19,'大一','南京邮电大学','人工智能学院','B202101193','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(192,194,NULL,'vip105','vip105的个人主页',449,25,14,'研一','南京邮电大学','社会与人口学院','B202001194','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(193,195,NULL,'customer101','customer101的个人主页',144,44,8,'大四','南京邮电大学','人工智能学院','B202101195','无糖鲜奶,健康茶饮,瑜伽伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(194,196,NULL,'customer102','customer102的个人主页',264,24,27,'大一','南京邮电大学','集成电路与微电子学院','B202001196','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(195,197,NULL,'customer103','customer103的个人主页',616,6,3,'大一','南京邮电大学','软件工程学院','B201901197','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(196,198,NULL,'customer104','customer104的个人主页',254,48,11,'大一','南京邮电大学','集成电路与微电子学院','B202001198','无糖正常冰,奶盖茶,摄影配茶','2026-04-03 16:23:32','2026-04-03 16:23:32'),(197,199,NULL,'customer105','customer105的个人主页',924,39,13,'研一','南京邮电大学','网络空间安全学院','B201901199','各地特色奶茶,旅行探店,芝士奶盖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(198,200,NULL,'buyer101','buyer101的个人主页',1052,12,12,'大二','南京邮电大学','计算机科学与技术学院','B201901200','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(199,201,NULL,'buyer102','buyer102的个人主页',315,3,22,'大三','南京邮电大学','计算机科学与技术学院','B202001201','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(200,202,NULL,'buyer103','buyer103的个人主页',1091,13,12,'大二','南京邮电大学','社会与人口学院','B202001202','热奶茶,暖心饮品,全糖热饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(201,203,NULL,'buyer104','buyer104的个人主页',872,14,3,'大四','南京邮电大学','软件工程学院','B202101203','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(202,204,NULL,'buyer105','buyer105的个人主页',106,1,3,'大三','南京邮电大学','集成电路与微电子学院','B202001204','冰沙系列,解渴神器,运动后必备','2026-04-03 16:23:32','2026-04-03 16:23:32'),(203,205,NULL,'seller101','seller101的个人主页',468,6,14,'大一','南京邮电大学','人工智能学院','B201901205','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(204,206,NULL,'seller102','seller102的个人主页',205,28,15,'研一','南京邮电大学','计算机科学与技术学院','B202101206','传统奶茶,古法制作,怀旧口味','2026-04-03 16:23:32','2026-04-03 16:23:32'),(205,207,NULL,'seller103','seller103的个人主页',229,39,17,'大三','南京邮电大学','社会与人口学院','B201901207','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(206,208,NULL,'seller104','seller104的个人主页',801,29,25,'大三','南京邮电大学','集成电路与微电子学院','B202101208','奶茶咖啡融合,鸳鸯奶茶,专业品鉴','2026-04-03 16:23:32','2026-04-03 16:23:32'),(207,209,NULL,'seller105','seller105的个人主页',134,49,26,'大三','南京邮电大学','网络空间安全学院','B202001209','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(208,210,NULL,'admin101','admin101的个人主页',204,4,4,'大三','南京邮电大学','人工智能学院','B202001210','各国奶茶,国际口味,学习伴侣','2026-04-03 16:23:32','2026-04-03 16:23:32'),(209,211,NULL,'admin102','admin102的个人主页',951,23,24,'大四','南京邮电大学','社会与人口学院','B202101211','热奶茶,暖心饮品,全糖热饮','2026-04-03 16:23:32','2026-04-03 16:23:32'),(210,212,NULL,'admin103','admin103的个人主页',508,23,2,'大一','南京邮电大学','人工智能学院','B201901212','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(211,213,NULL,'admin104','admin104的个人主页',699,6,25,'研一','南京邮电大学','集成电路与微电子学院','B202101213','焦糖玛奇朵,音乐伴侣,五分糖','2026-04-03 16:23:32','2026-04-03 16:23:32'),(212,214,NULL,'admin105','admin105的个人主页',1090,6,21,'大一','南京邮电大学','网络空间安全学院','B201901214','能量奶茶,通宵伴侣,珍珠加倍','2026-04-03 16:23:32','2026-04-03 16:23:32'),(213,215,NULL,'super101','super101的个人主页',128,15,15,'大三','南京邮电大学','网络空间安全学院','B202001215','全糖去冰,珍珠奶茶,芋圆控','2026-04-03 16:23:32','2026-04-03 16:23:32'),(214,216,NULL,'super102','super102的个人主页',777,16,17,'研一','南京邮电大学','集成电路与微电子学院','B202101216','DIY奶茶,自选配料,创意混搭','2026-04-03 16:23:32','2026-04-03 16:23:32'),(215,217,NULL,'super103','super103的个人主页',314,23,19,'研一','南京邮电大学','现代邮政学院','B202001217','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32'),(216,218,NULL,'super104','super104的个人主页',966,46,2,'大四','南京邮电大学','集成电路与微电子学院','B202101218','观影伴侣,爆米花奶茶,影院同款','2026-04-03 16:23:32','2026-04-03 16:23:32'),(217,219,NULL,'super105','super105的个人主页',617,31,17,'大一','南京邮电大学','现代邮政学院','B201901219','抹茶拿铁,艺术灵感,热饮党','2026-04-03 16:23:32','2026-04-03 16:23:32');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-04 10:32:34
