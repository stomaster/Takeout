package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    int batchInsert(@Param("items") List<OrderItem> items);
}