package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;  // 下单时的商品快照
    private BigDecimal productPrice;  // 下单时的价格快照
    private Integer quantity;
    private BigDecimal subtotal;  // 小计金额
    private LocalDateTime createTime;
}