package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.OrderDetail;
import com.food.order.mapper.OrderDetailMapper;
import com.food.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}