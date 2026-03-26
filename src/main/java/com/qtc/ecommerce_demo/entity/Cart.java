package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Cart {
    private Long cartId;
    private Long userId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productImage;
    private Integer quantity;
    private Boolean selected;  // 是否选中
    private BigDecimal totalAmount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 计算总价
    public void calculateTotal() {
        if (this.productPrice != null && this.quantity != null) {
            this.totalAmount = this.productPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}
/*
要求点击商品旁边的“加入购物车”即可加入，而且可以在product的库存stock大于0时一直加

在“我的购物车”里可以显示目前商品的总价，自动使用可用优惠券，显示优惠后的价格
 */