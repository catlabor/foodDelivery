package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.User;
import com.food.order.mapper.UserMapper;
import com.food.order.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
