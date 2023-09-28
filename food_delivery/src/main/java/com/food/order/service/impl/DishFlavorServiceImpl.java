package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.DishFlavor;
import com.food.order.mapper.DishFlavorMapper;
import com.food.order.service.DishFlavorService;
import org.springframework.stereotype.Service;


@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
