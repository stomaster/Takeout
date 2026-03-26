package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.Coupon;
import com.qtc.ecommerce_demo.entity.UserCoupon;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CouponMapper {
    // 查询所有优惠券
    @Select("SELECT * FROM coupon")
    List<Coupon> findAllCoupons();

    // 查询用户已领取的优惠券
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId}")
    List<UserCoupon> findUserCoupons(@Param("userId") Long userId);

    // 查询特定用户特定优惠券
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId} AND coupon_id = #{couponId}")
    UserCoupon findUserCoupon(@Param("userId") Long userId, @Param("couponId") Integer couponId);

    // 领取优惠券
    @Insert("INSERT INTO user_coupon (user_id, coupon_id, status, receive_time, expire_time) " +
            "VALUES (#{userId}, #{couponId}, 1, #{receiveTime}, #{expireTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertUserCoupon(UserCoupon userCoupon);

    // 使用优惠券
    @Update("UPDATE user_coupon SET status = 2, use_time = #{useTime}, order_id = #{orderId} " +
            "WHERE id = #{userCouponId} AND status = 1")
    int useCoupon(@Param("userCouponId") Long userCouponId,
                  @Param("useTime") LocalDateTime useTime,
                  @Param("orderId") Long orderId);

    /**
     * 以下是新增的购物车相关方法
     */

    // 根据ID查询优惠券
    @Select("SELECT * FROM coupon WHERE id = #{id}")
    Coupon findCouponById(@Param("id") Long id);

    // 查询用户可用优惠券
    @Select("SELECT c.* FROM coupon c " +
            "INNER JOIN user_coupon uc ON c.id = uc.coupon_id " +
            "WHERE uc.user_id = #{userId} " +
            "AND uc.status = 1 " +  // 1: 未使用
            "AND (c.expire_time IS NULL OR c.expire_time > NOW()) " +
            "AND (c.type = 1 OR (c.type = 2 AND c.min_amount <= #{totalAmount})) " +
            "ORDER BY c.amount DESC")
    List<Coupon> selectAvailableCoupons(@Param("userId") Long userId,
                                        @Param("totalAmount") Double totalAmount);

    // 查询未过期的优惠券
    @Select("SELECT * FROM coupon WHERE (expire_time IS NULL OR expire_time > NOW()) AND status = 1")
    List<Coupon> findValidCoupons();

    // 批量查询优惠券
    @Select({
            "<script>",
            "SELECT * FROM coupon WHERE id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Coupon> findCouponsByIds(@Param("ids") List<Long> ids);

    // 1. 检查用户是否已领取某优惠券
    @Select("SELECT COUNT(*) FROM user_coupon WHERE user_id = #{userId} AND coupon_id = #{couponId}")
    int checkUserHasCoupon(@Param("userId") Long userId, @Param("couponId") Integer couponId);

    // 2. 查询可领取的优惠券（基于canReceiveTime）
    @Select("SELECT * FROM coupon WHERE can_receive_time <= NOW() " +
            "AND id NOT IN (SELECT coupon_id FROM user_coupon WHERE user_id = #{userId})")
    List<Coupon> findAvailableCouponsForUser(Long userId);

    // 3. 查询过期的用户优惠券
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId} AND status = 1 AND expire_time < NOW()")
    List<UserCoupon> findExpiredUserCoupons(Long userId);
}