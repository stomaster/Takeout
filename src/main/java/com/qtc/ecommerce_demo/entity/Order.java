package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private String orderNo;  // 订单号
    private Long userId;
    private BigDecimal totalAmount;
    private Integer status;  // 1-待付款, 2-待发货, 3-待收货, 4-已完成, 5-已取消
    private LocalDateTime paymentTime;
    private LocalDateTime deliveryTime;
    private LocalDateTime receiveTime;
    private LocalDateTime cancelTime;
    private String cancelReason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}