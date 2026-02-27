package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Cart {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Boolean selected;  // 是否选中
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}