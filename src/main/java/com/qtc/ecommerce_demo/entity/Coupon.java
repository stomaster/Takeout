// Coupon.java
package com.qtc.ecommerce_demo.entity;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Coupon {
    private Long id;
    private Integer type;            // 1-无门槛, 2-满减
    private String name;
    private BigDecimal amount;
    private BigDecimal minAmount;
    private String description;
    private LocalDateTime canReceiveTime;
    private Integer expireDays;
    private LocalDateTime createdTime;
}
/*
两张优惠券，一张无门槛，一张至少花50r可用，
要有描述，可领取日期，领取后有效时长

领取该id后不能再领，时间未到不能领，

领取方式：先点击券，再点击“立即领取”

自动使用：满足最低起用金额，在有效期内

使用后，在支付旁边显示优惠后的价格

description都要显示。不可领取的券：按钮呈灰色并显示“暂不可领取”下面小字是canReceiveTime，可领取的券：显示“立即领取”


 */