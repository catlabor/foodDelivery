package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.ShoppingCart;
import com.food.order.mapper.ShoppingCartMapper;
import com.food.order.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartImpl extends ServiceImpl<ShoppingCartMapper,ShoppingCart> implements ShoppingCartService{
}
