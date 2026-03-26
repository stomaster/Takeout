package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.Cart;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CartMapper {

    // 查询用户购物车列表
    @Select("SELECT * FROM cart WHERE user_id = #{userId} ORDER BY update_time DESC")
    List<Cart> selectCartByUserId(@Param("userId") Long userId);

    // 查询购物车中的商品
    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    Cart selectByUserIdAndProductId(@Param("userId") Long userId,
                                    @Param("productId") Long productId);

    // 插入购物车记录
    @Insert("INSERT INTO cart (user_id, product_id, product_name, product_price, product_image, " +
            "quantity, selected, total_amount, create_time, update_time) " +
            "VALUES (#{userId}, #{productId}, #{productName}, #{productPrice}, #{productImage}, " +
            "#{quantity}, #{selected}, #{totalAmount}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "cartId", keyColumn = "cart_id")
    int insertCart(Cart cart);

    // 更新购物车商品数量
    @Update("UPDATE cart SET quantity = #{quantity}, total_amount = #{totalAmount}, " +
            "update_time = NOW() WHERE cart_id = #{cartId}")
    int updateCart(Cart cart);

    // 删除购物车商品
    @Delete("DELETE FROM cart WHERE cart_id = #{cartId}")
    int deleteCart(@Param("cartId") Long cartId);

    // 删除用户购物车商品
    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    int deleteByUserIdAndProductId(@Param("userId") Long userId,
                                   @Param("productId") Long productId);

    // 清空用户购物车
    @Delete("DELETE FROM cart WHERE user_id = #{userId}")
    int clearCartByUserId(@Param("userId") Long userId);

    // 更新选中状态
    @Update("UPDATE cart SET selected = #{selected}, update_time = NOW() " +
            "WHERE user_id = #{userId} AND product_id = #{productId}")
    int updateSelected(@Param("userId") Long userId,
                       @Param("productId") Long productId,
                       @Param("selected") Boolean selected);

    // 批量更新选中状态
    @Update("UPDATE cart SET selected = #{selected}, update_time = NOW() " +
            "WHERE user_id = #{userId}")
    int updateAllSelected(@Param("userId") Long userId,
                          @Param("selected") Boolean selected);

    // 查询选中的购物车商品
    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND selected = 1")
    List<Cart> selectSelectedByUserId(@Param("userId") Long userId);

    // 统计用户购物车商品数量
    @Select("SELECT COUNT(*) FROM cart WHERE user_id = #{userId}")
    Integer countCartItems(@Param("userId") Long userId);

    // 统计购物车商品总数量
    @Select("SELECT COALESCE(SUM(quantity), 0) FROM cart WHERE user_id = #{userId}")
    Integer sumCartQuantity(@Param("userId") Long userId);

    // 统计选中商品的总金额
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM cart WHERE user_id = #{userId} AND selected = 1")
    BigDecimal sumSelectedAmount(@Param("userId") Long userId);

    // 批量查询购物车商品
    @Select({
            "<script>",
            "SELECT * FROM cart",
            "WHERE user_id = #{userId}",
            "AND product_id IN",
            "<foreach item='id' collection='productIds' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Cart> selectByProductIds(@Param("userId") Long userId,
                                  @Param("productIds") List<Long> productIds);

    // 批量删除购物车商品
    @Delete({
            "<script>",
            "DELETE FROM cart",
            "WHERE user_id = #{userId}",
            "AND product_id IN",
            "<foreach item='id' collection='productIds' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteByProductIds(@Param("userId") Long userId,
                           @Param("productIds") List<Long> productIds);

    // CartMapper中可添加自动计算的方法
    @Update("UPDATE cart SET " +
            "quantity = #{quantity}, " +
            "total_amount = product_price * #{quantity}, " +  // 自动计算
            "update_time = NOW() " +
            "WHERE cart_id = #{cartId}")
    int updateCartQuantity(@Param("cartId") Long cartId, @Param("quantity") Integer quantity);
}