package com.qtc.ecommerce_demo.service;

import com.qtc.ecommerce_demo.dto.CartDTO;

public interface CartService {

    // 获取购物车详情
    CartDTO.Response getCart(Long userId);

    // 添加商品到购物车
    void addToCart(Long userId, Long productId, Integer quantity);

    // 更新购物车商品数量
    void updateCart(Long userId, Long productId, Integer quantity);

    // 删除购物车商品
    void deleteCartItem(Long userId, Long productId);

    // 清空购物车
    void clearCart(Long userId);

    // 选择/取消选择商品
    void selectItem(Long userId, Long productId, Boolean selected);

    // 全选/取消全选
    void selectAll(Long userId, Boolean selected);

    // 计算购物车优惠
    CartDTO.Response calculate(Long userId, Long couponId);

    // 获取购物车统计
    CartDTO.Summary getSummary(Long userId);
}