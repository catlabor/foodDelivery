package com.food.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.order.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
