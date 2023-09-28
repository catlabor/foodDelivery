package com.food.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.order.common.BaseContext;
import com.food.order.common.R;
import com.food.order.entity.Orders;
import com.food.order.entity.User;
import com.food.order.service.OrderService;
import com.food.order.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page> userpage(int page,int pageSize){
        Long currentId = BaseContext.getCurrentId();
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,currentId);
        userService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
}