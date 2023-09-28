package com.food.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.order.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
