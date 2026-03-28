package com.qtc.ecommerce_demo.service;
import com.qtc.ecommerce_demo.dto.CouponVO;
import com.qtc.ecommerce_demo.entity.Coupon;
import com.qtc.ecommerce_demo.entity.UserCoupon;
import com.qtc.ecommerce_demo.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponMapper couponMapper;

    // 获取用户优惠券列表
    // 获取用户优惠券列表
    public List<CouponVO> getUserCoupons(Long userId) {
        // 1. 获取所有优惠券
        List<Coupon> allCoupons = couponMapper.findAllCoupons();

        // 2. 获取用户已领取的优惠券
        List<UserCoupon> userCoupons = couponMapper.findUserCoupons(userId);
        Map<Integer, UserCoupon> userCouponMap = userCoupons.stream()
                .collect(Collectors.toMap(UserCoupon::getCouponId, uc -> uc));

        // 3. 组装返回数据
        LocalDateTime now = LocalDateTime.now();
        List<CouponVO> result = new ArrayList<>();

        for (Coupon coupon : allCoupons) {
            CouponVO vo = new CouponVO();
            vo.setCouponId(coupon.getId());
            vo.setName(coupon.getName());
            vo.setType(coupon.getType());
            vo.setAmount(coupon.getAmount());
            vo.setMinAmount(coupon.getMinAmount());
            vo.setDescription(coupon.getDescription());
            vo.setCanReceiveTime(coupon.getCanReceiveTime());

            UserCoupon userCoupon = userCouponMap.get(coupon.getId());

            if (userCoupon != null) {
                // 已领取
                vo.setReceiveStatus(2);
                vo.setUsageStatus(userCoupon.getStatus());
                vo.setExpireTime(userCoupon.getExpireTime());
                vo.setUserCouponId(userCoupon.getId());

                if (userCoupon.getStatus() == 1) {
                    vo.setButtonText("已领取");
                    vo.setButtonClass("btn-received");
                } else if (userCoupon.getStatus() == 2) {
                    vo.setButtonText("已使用");
                    vo.setButtonClass("btn-used");
                } else {
                    vo.setButtonText("已过期");
                    vo.setButtonClass("btn-expired");
                }
            } else {
                // 未领取
                if (now.isAfter(coupon.getCanReceiveTime()) || now.isEqual(coupon.getCanReceiveTime())) {
                    // 可领取
                    vo.setReceiveStatus(1);
                    vo.setButtonText("立即领取");  // ✅ 只改了这里：从未领取 -> 立即领取
                    vo.setButtonClass("btn-available");
                } else {
                    // 不可领取
                    vo.setReceiveStatus(0);
                    vo.setButtonText("暂不可领取");
                    vo.setButtonClass("btn-disabled");
                }
            }

            result.add(vo);
        }

        return result;
    }

    // 领取优惠券
    @Transactional
    public boolean receiveCoupon(Long userId, Integer couponId) {
        // 1. 检查优惠券是否存在
        Coupon coupon = couponMapper.findAllCoupons().stream()
                .filter(c -> c.getId().equals(couponId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        // 2. 检查是否可领取
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getCanReceiveTime())) {
            throw new RuntimeException("优惠券未到领取时间");
        }

        // 3. 检查是否已领取
        UserCoupon exist = couponMapper.findUserCoupon(userId, couponId);
        if (exist != null) {
            throw new RuntimeException("您已领取过此优惠券");
        }

        // 4. 领取优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus(1);
        userCoupon.setReceiveTime(now);
        userCoupon.setExpireTime(now.plusDays(coupon.getExpireDays()));

        return couponMapper.insertUserCoupon(userCoupon) > 0;
    }

    // 获取可用优惠券（付款时显示）
    public List<CouponVO> getAvailableCoupons(Long userId, BigDecimal orderAmount) {
        // 1. 获取用户已领取的优惠券
        List<UserCoupon> userCoupons = couponMapper.findUserCoupons(userId);

        // 2. 过滤可用的优惠券
        LocalDateTime now = LocalDateTime.now();
        List<CouponVO> availableCoupons = new ArrayList<>();

        for (UserCoupon uc : userCoupons) {
            if (uc.getStatus() != 1) continue;  // 只取未使用的

            if (uc.getExpireTime().isBefore(now)) {
                // 已过期，跳过
                continue;
            }

            // 获取优惠券详情
            Coupon coupon = couponMapper.findAllCoupons().stream()
                    .filter(c -> c.getId().equals(uc.getCouponId()))
                    .findFirst()
                    .orElse(null);

            if (coupon == null) continue;

            // 检查使用门槛
            if (orderAmount.compareTo(coupon.getMinAmount()) < 0) {
                continue;  // 金额不满足门槛
            }

            // 添加到可用列表
            CouponVO vo = new CouponVO();
            vo.setUserCouponId(uc.getId());
            vo.setName(coupon.getName());
            vo.setType(coupon.getType());
            vo.setAmount(coupon.getAmount());
            vo.setMinAmount(coupon.getMinAmount());
            vo.setDescription(coupon.getDescription());
            availableCoupons.add(vo);
        }

        return availableCoupons;
    }

    // 使用优惠券
    @Transactional
    public boolean useCoupon(Long userCouponId, Long orderId) {
        LocalDateTime now = LocalDateTime.now();
        return couponMapper.useCoupon(userCouponId, now, orderId) > 0;
    }
}