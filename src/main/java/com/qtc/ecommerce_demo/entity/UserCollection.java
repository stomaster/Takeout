package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class UserCollection {
    private Long id;
    private Long userId;
    private Long productId;
    private String productImage;
    private LocalDateTime createTime;
    public UserCollection() {}
    public UserCollection(Long userId, Long productId, Product product) {
        this.userId = userId;
        this.productId = productId;
        this.productImage = product.getImageUrl();
        this.productName = product.getName();      // 保存商品名称快照
        this.productPrice = product.getPrice();    // 保存商品价格快照
    }
    // 关联的商品信息
    private String productName;
    private BigDecimal productPrice;

    public String getProductDisplayName() {
        if (productName != null) {
            return productName;
        }
        return "商品#" + productId;
    }
    public boolean hasProductInfo() {
        return productName != null && productPrice != null;
    }
}
/*
用户收藏部分，点击“我的收藏”可以看到收藏的商品名字，图片，价格，productid
 */