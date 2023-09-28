package com.food.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.order.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
