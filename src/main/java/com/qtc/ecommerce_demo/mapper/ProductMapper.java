package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品实体
     */
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectById(@Param("id") Long id);

    /**
     * 查询所有在售商品
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE status = 1 ORDER BY create_time DESC")
    List<Product> selectAllAvailable();

    /**
     * 减少库存
     * @param id 商品ID
     * @param quantity 减少数量
     * @return 影响行数
     */
    @Update("UPDATE product SET stock = stock - #{quantity} WHERE id = #{id} AND stock >= #{quantity}")
    int reduceStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 插入商品
     * @param product 商品实体
     * @return 影响行数
     */
    @Insert("INSERT INTO product (" +
            "name, description, price, stock, category, " +
            "image_url, status, create_time, update_time" +
            ") VALUES (" +
            "#{name}, #{description}, #{price}, #{stock}, #{category}, " +
            "#{imageUrl}, #{status}, #{createTime}, #{updateTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Product product);

    /**
     * 更新商品
     * @param product 商品实体
     * @return 影响行数
     */
    @Update({"<script>",
            "UPDATE product ",
            "<set>",
            "  <if test='name != null'>name = #{name},</if>",
            "  <if test='description != null'>description = #{description},</if>",
            "  <if test='price != null'>price = #{price},</if>",
            "  <if test='stock != null'>stock = #{stock},</if>",
            "  <if test='category != null'>category = #{category},</if>",
            "  <if test='imageUrl != null'>image_url = #{imageUrl},</if>",
            "  <if test='status != null'>status = #{status},</if>",
            "  update_time = NOW()",
            "</set>",
            "WHERE id = #{id}",
            "</script>"})
    int update(Product product);

    /**
     * 根据分类查询商品
     * @param category 商品分类
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE category = #{category} AND status = 1 ORDER BY create_time DESC")
    List<Product> selectByCategory(@Param("category") String category);

    /**
     * 搜索商品（按名称或描述）
     * @param keyword 关键词
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE " +
            "(name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND status = 1 " +
            "ORDER BY create_time DESC")
    List<Product> search(@Param("keyword") String keyword);

    /**
     * 分页查询商品
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE status = 1 ORDER BY create_time DESC LIMIT #{limit} OFFSET #{offset}")
    List<Product> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询在售商品总数
     * @return 商品总数
     */
    @Select("SELECT COUNT(*) FROM product WHERE status = 1")
    int countAvailable();

    /**
     * 查询热门商品（按浏览量或销量）
     * @param limit 返回数量
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE status = 1 ORDER BY create_time DESC LIMIT #{limit}")
    List<Product> selectHotProducts(@Param("limit") Integer limit);

    /**
     * 批量查询商品
     * @param ids 商品ID列表
     * @return 商品列表
     */
    @Select({
            "<script>",
            "SELECT * FROM product",
            "WHERE id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "AND status = 1",
            "</script>"
    })
    List<Product> selectByIds(@Param("ids") List<Long> ids);

    /**
     * 增加库存
     * @param id 商品ID
     * @param quantity 增加数量
     * @return 影响行数
     */
    @Update("UPDATE product SET stock = stock + #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 查询库存
     * @param id 商品ID
     * @return 库存数量
     */
    @Select("SELECT stock FROM product WHERE id = #{id}")
    Integer getStock(@Param("id") Long id);

    /**
     * 更新商品状态
     * @param id 商品ID
     * @param status 状态 1-上架, 0-下架
     * @return 影响行数
     */
    @Update("UPDATE product SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Update("UPDATE product SET image_url = #{imageUrl} WHERE id = #{id}")
    int updateImageUrl(@Param("id") Long id, @Param("imageUrl") String imageUrl);

    /**
     * 以下是为购物车功能新增的方法
     */

    /**
     * 批量查询购物车中的商品
     * @param ids 商品ID列表
     * @return 商品列表
     */
    @Select({
            "<script>",
            "SELECT * FROM product",
            "<where>",
            "<if test='ids != null and !ids.isEmpty()'>",
            "id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</if>",
            "<if test='ids == null or ids.isEmpty()'>",
            "1=0",  // 当ids为空时，返回空结果
            "</if>",
            "</where>",
            "</script>"
    })
    List<Product> batchSelectByIds(@Param("ids") List<Long> ids);

    /**
     * 批量查询库存
     * @param ids 商品ID列表
     * @return 商品库存列表
     */
    @Select({
            "<script>",
            "SELECT id, stock FROM product",
            "WHERE id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Product> batchSelectStockByIds(@Param("ids") List<Long> ids);
}