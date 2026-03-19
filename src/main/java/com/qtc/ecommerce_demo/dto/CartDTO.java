package com.qtc.ecommerce_demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {

    // ===== 请求DTO =====
    /**
     * 添加/更新购物车商品请求
     */
    @Data
    public static class CartItemRequest {
        private Long productId;    // 商品ID
        private Integer quantity;  // 商品数量
    }

    /**
     * 选择/取消选择商品请求
     */
    @Data
    public static class SelectRequest {
        private Long productId;    // 商品ID
        private Boolean selected;  // 是否选中
    }

    /**
     * 计算优惠请求
     */
    @Data
    public static class CalculateRequest {
        private Long couponId;     // 优惠券ID
    }

    /**
     * 批量选择请求
     */
    @Data
    public static class BatchSelectRequest {
        private List<Long> productIds;  // 商品ID列表
        private Boolean selected;        // 是否选中
    }

    // ===== 响应DTO =====
    /**
     * 购物车商品项
     */
    @Data
    public static class Item {
        private Long cartId;         // 购物车ID
        private Long productId;      // 商品ID
        private String productName;  // 商品名称
        private BigDecimal price;    // 商品单价
        private String imageUrl;     // 商品图片
        private Integer quantity;    // 商品数量
        private Boolean selected;    // 是否选中
        private BigDecimal subtotal; // 商品小计
        private Integer status;      // 商品状态
        private Integer stock;       // 商品库存
    }

    /**
     * 可用优惠券
     */
    @Data
    public static class Coupon {
        private Long couponId;       // 优惠券ID
        private String name;         // 优惠券名称
        private BigDecimal amount;   // 优惠金额
        private BigDecimal minAmount; // 最低消费
        private Integer type;        // 优惠券类型
        private String description;  // 优惠券描述
        private Boolean available;   // 是否可用
    }

    /**
     * 购物车完整响应
     */
    @Data
    public static class Response {
        private List<Item> items;           // 购物车商品列表
        private List<Coupon> coupons;       // 可用优惠券列表
        private BigDecimal totalAmount;     // 商品总金额
        private BigDecimal discountAmount;  // 优惠金额
        private BigDecimal finalAmount;     // 最终金额
        private Integer totalQuantity;      // 商品总数量
        private Integer itemCount;          // 商品种类数
        private Long selectedCouponId;      // 选中的优惠券ID
    }

    /**
     * 购物车统计数据
     */
    @Data
    public static class Summary {
        private Integer itemCount;          // 购物车商品总数
        private BigDecimal totalAmount;     // 总金额
        private Integer selectedCount;      // 选中商品数量
    }
}