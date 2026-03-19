package com.qtc.ecommerce_demo.dto;

import lombok.Data;

@Data
public class CollectionRequest {
    // 不要验证注解
    private Long productId;
    private String productImage;
}