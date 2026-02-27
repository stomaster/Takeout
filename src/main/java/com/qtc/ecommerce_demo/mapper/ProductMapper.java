package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectById(@Param("id") Long id);

    @Select("SELECT * FROM product WHERE status = 1 ORDER BY create_time DESC")
    List<Product> selectAllAvailable();

    @Update("UPDATE product SET stock = stock - #{quantity} WHERE id = #{id} AND stock >= #{quantity}")
    int reduceStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    int insert(Product product);

    int update(Product product);
}