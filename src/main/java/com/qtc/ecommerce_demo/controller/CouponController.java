package com.qtc.ecommerce_demo.controller;
import com.qtc.ecommerce_demo.dto.CouponVO;
import com.qtc.ecommerce_demo.entity.Coupon;
import com.qtc.ecommerce_demo.entity.UserCoupon;
import com.qtc.ecommerce_demo.mapper.CouponMapper;
import com.qtc.ecommerce_demo.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final CouponMapper couponMapper;

    // 获取优惠券列表
    @GetMapping("/list")
    public Map<String, Object> getCouponList(@RequestParam Long userId) {
        List<CouponVO> coupons = couponService.getUserCoupons(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", coupons);
        return result;
    }

    // 领取优惠券
    @PostMapping("/receive")
    public Map<String, Object> receiveCoupon(@RequestParam Long userId,
                                             @RequestParam Integer couponId) {
        try {
            boolean success = couponService.receiveCoupon(userId, couponId);
            Map<String, Object> result = new HashMap<>();
            if (success) {
                result.put("code", 200);
                result.put("message", "领取成功");
            } else {
                result.put("code", 500);
                result.put("message", "领取失败");
            }
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 400);
            result.put("message", e.getMessage());
            return result;
        }
    }

    // 获取可用优惠券（付款时调用）
    @GetMapping("/available")
    public Map<String, Object> getAvailableCoupons(@RequestParam Long userId,
                                                   @RequestParam BigDecimal orderAmount) {
        List<CouponVO> coupons = couponService.getAvailableCoupons(userId, orderAmount);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", coupons);
        return result;
    }

    // 使用优惠券
    @PostMapping("/use")
    public Map<String, Object> useCoupon(@RequestParam Long userCouponId,
                                         @RequestParam Long orderId) {
        boolean success = couponService.useCoupon(userCouponId, orderId);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "使用成功");
        } else {
            result.put("code", 500);
            result.put("message", "使用失败");
        }
        return result;
    }



    @PostMapping("/debug/receive")
    public Map<String, Object> debugReceiveCoupon(@RequestParam Long userId,
                                                  @RequestParam Integer couponId) {
        Map<String, Object> result = new HashMap<>();

        try {
            System.out.println("=== 开始调试领取优惠券 ===");
            System.out.println("用户ID: " + userId + ", 优惠券ID: " + couponId);

            // 1. 检查优惠券是否存在
            List<Coupon> allCoupons = couponMapper.findAllCoupons();
            Coupon coupon = allCoupons.stream()
                    .filter(c -> c.getId().equals(couponId))
                    .findFirst()
                    .orElse(null);

            if (coupon == null) {
                result.put("code", 400);
                result.put("message", "优惠券不存在");
                result.put("step", "检查优惠券");
                return result;
            }
            System.out.println("找到优惠券: " + coupon.getName());

            // 2. 检查是否可领取
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(coupon.getCanReceiveTime())) {
                result.put("code", 400);
                result.put("message", "优惠券未到领取时间");
                result.put("step", "检查时间");
                return result;
            }
            System.out.println("时间检查通过");

            // 3. 检查是否已领取
            UserCoupon exist = couponMapper.findUserCoupon(userId, couponId);
            if (exist != null) {
                result.put("code", 400);
                result.put("message", "已领取过此优惠券");
                result.put("step", "检查重复");
                result.put("existRecord", exist);
                return result;
            }
            System.out.println("重复检查通过");

            // 4. 创建用户优惠券记录
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUserId(userId);
            userCoupon.setCouponId(couponId);
            userCoupon.setStatus(1);
            userCoupon.setReceiveTime(now);
            userCoupon.setExpireTime(now.plusDays(coupon.getExpireDays()));

            System.out.println("准备插入: " + userCoupon);

            // 5. 执行插入
            int insertResult = couponMapper.insertUserCoupon(userCoupon);
            System.out.println("插入结果: " + insertResult + " 行受影响");
            System.out.println("生成的ID: " + userCoupon.getId());

            if (insertResult > 0) {
                result.put("code", 200);
                result.put("message", "插入成功");
                result.put("affectedRows", insertResult);
                result.put("generatedId", userCoupon.getId());
                result.put("data", userCoupon);
            } else {
                result.put("code", 500);
                result.put("message", "插入失败，返回0行");
                result.put("affectedRows", insertResult);
            }

        } catch (Exception e) {
            System.out.println("发生异常: " + e.getMessage());
            e.printStackTrace();

            result.put("code", 500);
            result.put("message", "服务器错误: " + e.getMessage());
            result.put("exception", e.getClass().getName());
        }

        return result;
    }

    // 查看数据库记录
    @GetMapping("/debug/records")
    public Map<String, Object> debugRecords(@RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<UserCoupon> userCoupons = couponMapper.findUserCoupons(userId);
            List<Coupon> allCoupons = couponMapper.findAllCoupons();

            result.put("code", 200);
            result.put("message", "success");
            result.put("userCoupons", userCoupons);
            result.put("allCoupons", allCoupons);
            result.put("userCouponCount", userCoupons.size());
            result.put("allCouponCount", allCoupons.size());

        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败: " + e.getMessage());
        }

        return result;
    }
}
