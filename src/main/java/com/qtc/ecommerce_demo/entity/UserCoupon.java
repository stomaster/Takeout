package com.qtc.ecommerce_demo.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCoupon {
    private Long id;
    private Long userId;
    private Integer couponId;
    private Integer status;          // 1-未使用 2-已使用 3-已过期
    private LocalDateTime receiveTime;
    private LocalDateTime expireTime;
    private LocalDateTime useTime;
    private Long orderId;
}
/*
领取成功后在“我的优惠券”里可以看到已经领取的券，要求显示他们的status，receivetime，expiretime
使用过的券还要显示usetime和orderId
 */