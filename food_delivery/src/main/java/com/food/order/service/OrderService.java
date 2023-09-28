package com.food.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.food.order.entity.Orders;

public interface OrderService extends IService<Orders> {


    public void submit(Orders orders);
}
