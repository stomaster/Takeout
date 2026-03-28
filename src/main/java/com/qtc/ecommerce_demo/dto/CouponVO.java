package com.qtc.ecommerce_demo.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponVO {
    private Long couponId;
    private String name;
    private Integer type;
    private BigDecimal amount;
    private BigDecimal minAmount;
    private String description;
    private LocalDateTime canReceiveTime;

    // 领取状态: 0-不可领 1-可领取 2-已领取
    private Integer receiveStatus = 0;

    // 已领取时才有的信息
    private Integer usageStatus;     // 1-未使用 2-已使用
    private LocalDateTime expireTime;
    private Long userCouponId;

    // 前端显示文本
    private String buttonText = "暂不可领取";
    private String buttonClass = "btn-disabled";
}