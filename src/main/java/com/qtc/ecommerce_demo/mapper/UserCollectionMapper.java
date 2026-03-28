package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.UserCollection;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserCollectionMapper {

    /**
     * 查询用户收藏列表 - 关联商品表获取最新商品信息
     * 优点：商品信息实时更新，不存储冗余数据，保证数据一致性
     */
    @Select("SELECT " +
            "   uc.id, " +
            "   uc.user_id as userId, " +
            "   uc.product_id as productId, " +
            "   uc.create_time as createTime, " +
            "   p.name as productName, " +         // 从商品表获取最新名称
            "   p.price as productPrice, " +       // 从商品表获取最新价格
            "   p.image_url as productImage, " +   // 从商品表获取最新图片
            "   p.description as productDescription, " + // 可选：商品描述
            "   p.stock as productStock " +        // 可选：商品库存
            "FROM user_collection uc " +
            "LEFT JOIN product p ON uc.product_id = p.id " +  // 关键：关联商品表
            "WHERE uc.user_id = #{userId} " +
            "ORDER BY uc.create_time DESC")
    List<UserCollection> selectByUserId(Long userId);

    /**
     * 添加收藏
     * 注意：只存储user_id和product_id，不存储冗余的商品信息
     */
    @Insert("INSERT INTO user_collection (user_id, product_id, create_time) " +
            "VALUES (#{userId}, #{productId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserCollection userCollection);

    /**
     * 删除收藏
     */
    @Delete("DELETE FROM user_collection WHERE user_id = #{userId} AND product_id = #{productId}")
    int delete(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 检查是否已收藏
     * 统计：统计满足条件的记录有多少条
     *
     * 条件：user_id = 123 AND product_id = 456
     *
     * 返回值：满足条件的行数（整数）
     */
    @Select("SELECT COUNT(*) FROM user_collection WHERE user_id = #{userId} AND product_id = #{productId}")
    int exists(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据ID删除收藏
     */
    @Delete("DELETE FROM user_collection WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 清空用户所有收藏
     */
    @Delete("DELETE FROM user_collection WHERE user_id = #{userId}")
    int clearByUserId(Long userId);
}