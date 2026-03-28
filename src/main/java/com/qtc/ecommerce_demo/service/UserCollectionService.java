package com.qtc.ecommerce_demo.service;

import com.qtc.ecommerce_demo.entity.Product;
import com.qtc.ecommerce_demo.entity.UserCollection;
import com.qtc.ecommerce_demo.mapper.UserCollectionMapper;
import com.qtc.ecommerce_demo.mapper.ProductMapper;
import com.qtc.ecommerce_demo.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCollectionService {

    private final UserCollectionMapper userCollectionMapper;
    private final ProductMapper productMapper;
    private final UserProfileMapper userProfileMapper;  // ✅ 添加：用于更新收藏数

    /**
     * 用户收藏商品
     */
    @Transactional
    public boolean addCollection(Long userId, Long productId) {
        // 1. 检查是否已收藏
        if (userCollectionMapper.exists(userId, productId) > 0) {
            return false; // 已收藏
        }

        // 2. 查询商品信息
        Product product = productMapper.selectById(productId);
        if (product == null || product.getStatus() != 1) {
            throw new RuntimeException("商品不存在或已下架");
        }

        // 3. 创建收藏记录
        UserCollection collection = new UserCollection();
        collection.setUserId(userId);
        collection.setProductId(productId);
        collection.setProductImage(product.getImageUrl() != null ?
                product.getImageUrl() : "/default-product.jpg");
        collection.setProductName(product.getName());      // 保存商品名称
        collection.setProductPrice(product.getPrice());    // 保存商品价格

        // 4. 保存到数据库
        int result = userCollectionMapper.insert(collection);

        // 5. ✅ 更新用户收藏数
        if (result > 0) {
            userProfileMapper.incrementCollectCount(userId);
        }

        return result > 0;
    }

    /**
     * 获取用户的收藏列表
     */
    public List<UserCollection> getUserCollections(Long userId) {
        return userCollectionMapper.selectByUserId(userId);
    }

    /**
     * 取消收藏
     */
    @Transactional
    public boolean removeCollection(Long userId, Long productId) {
        try {
            System.out.println("=== 开始取消收藏 ===");
            System.out.println("用户ID: " + userId + ", 商品ID: " + productId);

            // 1. 删除收藏记录
            int deleteResult = userCollectionMapper.delete(userId, productId);
            System.out.println("删除收藏记录结果: " + deleteResult);

            // 2. 减少收藏数
            if (deleteResult > 0) {
                System.out.println("开始减少收藏数...");
                int updateResult = userProfileMapper.decrementCollectCount(userId);
                System.out.println("减少收藏数SQL执行结果: " + updateResult);

                if (updateResult <= 0) {
                    System.err.println("警告：减少收藏数失败！可能原因：");
                    System.err.println("1. 用户不存在");
                    System.err.println("2. collect_count字段不存在");
                    System.err.println("3. SQL语法错误");
                } else {
                    System.out.println("成功减少收藏数");
                }
            }

            return deleteResult > 0;
        } catch (Exception e) {
            System.err.println("取消收藏过程中发生异常:");
            e.printStackTrace();
            throw new RuntimeException("取消收藏失败: " + e.getMessage(), e);
        }
    }

    /**
     * 检查是否已收藏
     */
    public boolean isCollected(Long userId, Long productId) {
        return userCollectionMapper.exists(userId, productId) > 0;
    }
}