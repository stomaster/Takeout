package com.qtc.ecommerce_demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductQueryDTO {

    /**
     * 搜索关键词（商品名称或描述）
     */
    private String keyword;

    /**
     * 商品分类
     */
    private String category;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;

    /**
     * 最低库存
     */
    private Integer minStock;

    /**
     * 最高库存
     */
    private Integer maxStock;

    /**
     * 商品状态（1-上架, 0-下架）
     */
    private Integer status;

    /**
     * 排序字段（如：price, create_time, stock）
     */
    private String sortField;

    /**
     * 排序方式（asc-升序, desc-降序）
     */
    private String sortOrder = "desc";

    /**
     * 页码（用于分页）
     */
    private Integer page = 1;

    /**
     * 每页大小（用于分页）
     */
    private Integer size = 10;
}